package me.rhin.openciv.shared.stat;

public enum Stat {

	GOLD,
	GOLD_GAIN(GOLD),
	MAINTENANCE(GOLD),
	HERITAGE,
	HERITAGE_GAIN(HERITAGE),
	FAITH,
	FAITH_GAIN(FAITH),
	SCIENCE_GAIN,
	HEALTH,
	HEALTH_GAIN(HEALTH),
	PRODUCTION_GAIN(StatType.CITY_EXCLUSIVE),
	FOOD_SURPLUS(StatType.CITY_EXCLUSIVE),
	FOOD_GAIN(StatType.CITY_EXCLUSIVE),
	POPULATION(StatType.CITY_EXCLUSIVE),
	EXPANSION_REQUIREMENT(StatType.CITY_EXCLUSIVE),
	EXPANSION_PROGRESS(StatType.CITY_EXCLUSIVE),
	POLICY_COST(StatType.POLICY_EXCLUSIVE),
	COMBAT_STRENGTH,
	TRADE_ROUTE_AMOUNT(StatType.PLAYER_EXCLUSIVE),
	MAX_TRADE_ROUTES(StatType.PLAYER_EXCLUSIVE),
	MORALE_TILE,
	MORALE_CITY(StatType.CITY_EXCLUSIVE),
	TRADE_GOLD_MODIFIER(StatType.CITY_EXCLUSIVE);

	private Stat addedStat;
	private StatType statType;

	private Stat() {
	}

	private Stat(Stat addedStat) {
		this.addedStat = addedStat;
	}

	private Stat(StatType statType) {
		this.statType = statType;
	}

	private Stat(StatType statType, Stat addedStat) {
		this.addedStat = addedStat;
		this.statType = statType;
	}

	public boolean isGained() {
		return addedStat != null;
	}

	public Stat getAddedStat() {
		return addedStat;
	}

	public StatType getStatType() {
		return statType;
	}
}
