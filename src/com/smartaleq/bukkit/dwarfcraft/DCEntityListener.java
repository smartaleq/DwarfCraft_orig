package com.smartaleq.bukkit.dwarfcraft;

import java.util.HashMap;
import java.util.List;

import org.bukkit.craftbukkit.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import redecouverte.npcspawner.NpcEntityTargetEvent;
import redecouverte.npcspawner.NpcEntityTargetEvent.NpcTargetReason;

class DCEntityListener extends EntityListener {
	private final DwarfCraft plugin;
	private HashMap<Entity, Dwarf> killMap;

	protected DCEntityListener(DwarfCraft plugin) {
		this.plugin = plugin;
		killMap = new HashMap<Entity, Dwarf>();
	}

	private boolean checkDwarfTrainer(EntityDamageByEntityEvent event) {
		// all we know right now is that event.entity instanceof HumanEntity
		DwarfTrainer trainer = plugin.getDataManager().getTrainer(
				event.getEntity());
		if (trainer != null) {
			if (event.getDamager() instanceof Player) {
				// in business, left click
				if (trainer.isGreeter()) {
					trainer.printLeftClick((Player) (event.getDamager()));
				} else {
					trainer.lookAt(event.getDamager());
					Player player = (Player) event.getDamager();
					Dwarf dwarf = plugin.getDataManager().find(player);
					Skill skill = dwarf.getSkill(trainer.getSkillTrained());
					int maxSkill = trainer.getMaxSkill();
					plugin.getOut().printSkillInfo(player, skill, dwarf,
							maxSkill);
				}
			}
			return true;
		}
		return false;
	}

	private boolean checkDwarfTrainer(NpcEntityTargetEvent event) {
		try {
			Dwarf dwarf = plugin.getDataManager().find(
					((Player) event.getTarget()));
			DwarfTrainer trainer = plugin.getDataManager().getTrainer(
					event.getEntity());
			if (trainer != null) {
				if (event.getTarget() instanceof Player) {
					if (event.getNpcReason() == NpcTargetReason.CLOSEST_PLAYER) {
					} else if (event.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED) {
						if (trainer.isGreeter()) {
							trainer.printRightClick((Player) (event.getTarget()));
						} else {
							trainer.lookAt(event.getTarget());
							trainer.getBasicHumanNpc().animateArmSwing();
							trainer.trainSkill(dwarf);
						}
					} else if (event.getNpcReason() == NpcTargetReason.NPC_BOUNCED) {
						// player collided with mob
						// doesn't seem to work
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void onEntityAttack(EntityDamageByEntityEvent event) {
		if (DwarfCraft.disableEffects)
			return;
		Entity damager = event.getDamager();
		LivingEntity victim;
		if (event.getEntity() instanceof LivingEntity)
			victim = (LivingEntity) event.getEntity();
		else
			return;
		boolean isPVP = false;
		Dwarf attacker = null;
		if (victim instanceof Player) {
			isPVP = true;
		}
		int damage = event.getDamage();
		int hp = victim.getHealth();
		if (damager instanceof Player)
			attacker = plugin.getDataManager().find((Player) damager);
		// EvP no effects, EvE no effects
		else {
			if (DwarfCraft.debugMessagesThreshold < 4)
				System.out.println("DC4: EVP "
						+ damager.getClass().getSimpleName() + " attacked "
						+ victim.getClass().getSimpleName() + " for " + damage
						+ " of " + hp);
			return;
		}
		ItemStack tool = attacker.getPlayer().getItemInHand();
		int toolId = -1;
		short durability = 0;
		if (tool != null) {
			toolId = tool.getTypeId();
			durability = tool.getDurability();
		}
		boolean sword = false;
		List<Skill> skills = attacker.getSkills();
		for (Skill s : skills) {
			if (s == null)
				continue;
			for (Effect e : s.getEffects()) {
				if (e == null)
					continue;
				if (e.getEffectType() == EffectType.SWORDDURABILITY) {
					for (int id : e.getTools()) {
						if (id == toolId) {
							sword = true;
							double effectAmount = e.getEffectAmount(attacker);

							if (DwarfCraft.debugMessagesThreshold < 2)
								System.out
										.println("DC2: affected durability of a sword - old:"
												+ durability
												+ " effect called:" + e.getId());
							tool.setDurability((short) (durability + Util
									.randomAmount(effectAmount)));
							if (DwarfCraft.debugMessagesThreshold < 3)
								System.out
										.println("DC3: affected durability of a sword - new:"
												+ tool.getDurability());
							Util.toolChecker((Player) damager);
						}
					}
				}
				if (e.getEffectType() == EffectType.PVEDAMAGE && !isPVP
						&& sword) {
					if (hp <= 0) {
						event.setCancelled(true);
						return;
					}
					damage = Util.randomAmount((e.getEffectAmount(attacker))
							* damage);
					if (damage >= hp && !killMap.containsKey(victim)){
		                killMap.put(victim, attacker);
					}
					event.setDamage(damage);
					if (DwarfCraft.debugMessagesThreshold < 6)
						System.out.println("DC6: PVE "
								+ attacker.getPlayer().getName() + " attacked "
								+ victim.getClass().getSimpleName() + " for "
								+ e.getEffectAmount(attacker) + " of "
								+ event.getDamage() + " doing " + damage
								+ " dmg of " + hp + "hp" + " effect called:"
								+ e.getId());
				}
				if (e.getEffectType() == EffectType.PVPDAMAGE && isPVP && sword) {
					damage = Util.randomAmount((e.getEffectAmount(attacker))
							* damage);
					event.setDamage(damage);
					if (DwarfCraft.debugMessagesThreshold < 6)
						System.out.println("DC6: PVP "
								+ attacker.getPlayer().getName() + " attacked "
								+ ((Player) victim).getName() + " for "
								+ e.getEffectAmount(attacker) + " of "
								+ event.getDamage() + " doing " + damage
								+ " dmg of " + hp + "hp" + " effect called:"
								+ e.getId());
				}
			}
		}
	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event instanceof EntityDamageByEntityEvent) {
			if (event.getEntity() instanceof HumanEntity) {
				if (checkDwarfTrainer((EntityDamageByEntityEvent) event)) { 
					event.setCancelled(true);
					return;
				}
			}
		}
		if (event.isCancelled())
			return;
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION
				|| event.getCause() == DamageCause.ENTITY_EXPLOSION
				|| event.getCause() == DamageCause.FALL
				|| event.getCause() == DamageCause.SUFFOCATION
				|| event.getCause() == DamageCause.FIRE
				|| event.getCause() == DamageCause.FIRE_TICK) {
			if (DwarfCraft.debugMessagesThreshold < -1)
				System.out.println("DC-1: Damage Event: environment");
			onEntityDamagedByEnvirons(event);

		} else if (event instanceof EntityDamageByProjectileEvent) {
			EntityDamageByProjectileEvent sub = (EntityDamageByProjectileEvent) event;
			if (DwarfCraft.debugMessagesThreshold < 2)
				System.out.println("DC4: Damage Event: projectile");
			onEntityDamageByProjectile(sub);
		} else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent sub = (EntityDamageByEntityEvent) event;
			if (DwarfCraft.debugMessagesThreshold < 2)
				System.out.println("DC4: Damage Event: entity by entity");
			onEntityAttack(sub);
		} else
			return;
	}

	public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
		if (DwarfCraft.disableEffects)
			return;
		if (!(event.getDamager() instanceof Player))
			return;
		Dwarf dwarf = plugin.getDataManager().find((Player) event.getDamager());
		LivingEntity hitThing = ((LivingEntity) event.getEntity());
		int hp = hitThing.getHealth();
		if (hp <= 0) {
			event.setCancelled(true);
			return;
		}
		int damage;
		for (Skill s : dwarf.getSkills()) {
			if (s == null)
				continue;
			for (Effect e : s.getEffects()) {
				if (e == null)
					continue;
				if (e.getEffectType() == EffectType.BOWATTACK) {
					damage = Util.randomAmount((e.getEffectAmount(dwarf)));
					event.setDamage(damage);
					if (damage >= hp && !killMap.containsKey(hitThing) && !(hitThing instanceof Player)){
		                killMap.put(hitThing, dwarf);
					}
					if (DwarfCraft.debugMessagesThreshold < 7)
						System.out.println("DC7: PVP "
								+ dwarf.getPlayer().getName() + " shot "
								+ hitThing.getClass().getSimpleName() + " for "
								+ damage + " of " + hp + " eventdmg:"
								+ event.getDamage() + " effect called:"
								+ e.getId());

				}
			}
		}
	}

	public void onEntityDamagedByEnvirons(EntityDamageEvent event) {
		if (DwarfCraft.disableEffects)
			return;
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		Dwarf dwarf = plugin.getDataManager().find(player);
		List<Skill> skills = dwarf.getSkills();
		int damage = event.getDamage();
		for (Skill s : skills) {
			if (s == null)
				continue;
			for (Effect e : s.getEffects()) {
				if (e == null)
					continue;
				if ((e.getEffectType() == EffectType.FALLDAMAGE && event
						.getCause() == DamageCause.FALL)
						|| (e.getEffectType() == EffectType.FALLDAMAGE && event
								.getCause() == DamageCause.SUFFOCATION)
						|| (e.getEffectType() == EffectType.FIREDAMAGE && event
								.getCause() == DamageCause.FIRE)
						|| (e.getEffectType() == EffectType.FIREDAMAGE && event
								.getCause() == DamageCause.FIRE_TICK)
						|| (e.getEffectType() == EffectType.EXPLOSIONDAMAGE && event
								.getCause() == DamageCause.ENTITY_EXPLOSION)
						|| (e.getEffectType() == EffectType.EXPLOSIONDAMAGE && event
								.getCause() == DamageCause.BLOCK_EXPLOSION)) {
					damage = (int) Math
							.floor((e.getEffectAmount(dwarf) * damage));
					if (DwarfCraft.debugMessagesThreshold < 1)
						System.out.println("DC1: environment damage type:"
								+ event.getCause() + " base damage:"
								+ event.getDamage() + " new damage:" + damage
								+ " effect called:"
								+ e.getId());
					event.setDamage(damage);
				}
			}
			for (Effect e : s.getEffects()) {
				if (e.getEffectType() == EffectType.FALLTHRESHOLD
						&& event.getCause() == DamageCause.FALL) {
					if (event.getDamage() <= e.getEffectAmount(dwarf)){
						if (DwarfCraft.debugMessagesThreshold < 1)
							System.out.println("DC1: Damage less than fall threshold");
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if (DwarfCraft.disableEffects)
			return;
		Entity deadThing = event.getEntity();
		if (deadThing instanceof Player)
			return;
		List<ItemStack> items = event.getDrops();
		items.clear();
		if (killMap.containsKey(event.getEntity())){
			Dwarf killer = killMap.get(deadThing);
			for (Skill s : killer.getSkills()) {
				if (s == null)
					continue;
				for (Effect e : s.getEffects()) {
					if (e == null)
						continue;
					if (e.getEffectType() == EffectType.MOBDROP) {
						if ((e.getId() == 810 && (deadThing instanceof CraftPig))
								|| (e.getId() == 811 && (deadThing instanceof CraftCow))
								|| (e.getId() == 820 && (deadThing instanceof CraftCreeper))
								|| (e.getId() == 823 && (deadThing instanceof CraftSpider))
								|| (e.getId() == 821 && (deadThing instanceof CraftSkeleton))
								|| (e.getId() == 822 && (deadThing instanceof CraftSkeleton))
								|| (e.getId() == 850 && (deadThing instanceof CraftZombie))
								|| (e.getId() == 851 && (deadThing instanceof CraftZombie))
								|| (e.getId() == 852 && (deadThing instanceof CraftChicken))) {

							if (DwarfCraft.debugMessagesThreshold < 5)
								System.out.println("DC5: killed a "
										+ deadThing.getClass().getSimpleName()
										+ " effect called:" + e.getId());
							items.add(new ItemStack(e.getOutputId(),Util.randomAmount(e.getEffectAmount(killer))));
						}
					}
				}
			}
		}	
	}

	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		if (event instanceof NpcEntityTargetEvent) {
			checkDwarfTrainer((NpcEntityTargetEvent) event);
			event.setCancelled(true);
		}
		return;
	}
}
