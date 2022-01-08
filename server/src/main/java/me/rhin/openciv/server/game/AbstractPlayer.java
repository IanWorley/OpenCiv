package me.rhin.openciv.server.game;

import java.util.ArrayList;

import me.rhin.openciv.server.Server;
import me.rhin.openciv.server.game.city.City;
import me.rhin.openciv.server.game.city.building.Building;
import me.rhin.openciv.server.game.civilization.Civ;
import me.rhin.openciv.server.game.civilization.CivType;
import me.rhin.openciv.server.game.diplomacy.Diplomacy;
import me.rhin.openciv.server.game.heritage.HeritageTree;
import me.rhin.openciv.server.game.religion.PlayerReligion;
import me.rhin.openciv.server.game.research.ResearchTree;
import me.rhin.openciv.server.game.unit.Unit;
import me.rhin.openciv.server.game.unit.type.Prophet;
import me.rhin.openciv.server.listener.NextTurnListener;
import me.rhin.openciv.shared.stat.StatLine;
import me.rhin.openciv.shared.stat.StatType;

public abstract class AbstractPlayer implements NextTurnListener {

	protected ArrayList<City> ownedCities;
	protected ArrayList<Unit> ownedUnits;
	protected Diplomacy diplomacy;
	protected StatLine statLine;
	protected ResearchTree researchTree;
	protected HeritageTree heritageTree;
	protected PlayerReligion playerReligion;
	protected Civ civilization;
	private int spawnX;
	private int spawnY;

	public AbstractPlayer() {

		this.ownedCities = new ArrayList<>();
		this.ownedUnits = new ArrayList<>();

		this.diplomacy = new Diplomacy(this);
		this.statLine = new StatLine();

		this.researchTree = new ResearchTree(this);
		this.heritageTree = new HeritageTree(this);
		this.playerReligion = new PlayerReligion(this);

		this.spawnX = -1;
		this.spawnY = -1;

		Server.getInstance().getEventManager().addListener(NextTurnListener.class, this);
	}

	public abstract String getName();

	public abstract void sendPacket(String json);

	public abstract boolean hasConnection();

	public abstract void setSelectedUnit(Unit unit);

	@Override
	public void onNextTurn() {
		if (ownedCities.size() < 1)
			return;

		updateOwnedStatlines(true);
	}

	public void updateOwnedStatlines(boolean increaseValues) {

		// Clear previous values other than accumulative ones.
		statLine.clearNonAccumulative();

		if (ownedCities.size() > 0)
			for (Unit unit : ownedUnits) {
				statLine.mergeStatLine(unit.getMaintenance());
			}

		for (City city : ownedCities) {
			statLine.mergeStatLineExcluding(city.getStatLine(), StatType.CITY_EXCLUSIVE);
		}

		if (increaseValues)
			statLine.updateStatLine();

	}

	public void reduceStatLine(StatLine statLine) {
		this.statLine.reduceStatLine(statLine);
	}

	public void addOwnedUnit(Unit unit) {
		ownedUnits.add(unit);
	}

	public void removeCity(City city) {
		ownedCities.remove(city);
	}

	public void addCity(City city) {
		ownedCities.add(city);
	}

	public ResearchTree getResearchTree() {
		return researchTree;
	}

	public HeritageTree getHeritageTree() {
		return heritageTree;
	}

	public StatLine getStatLine() {
		return statLine;
	}

	public boolean hasBuilt(Class<? extends Building> buildingClass) {
		for (City city : ownedCities) {
			for (Building building : city.getBuildings()) {
				if (building.getClass() == buildingClass)
					return true;
			}
		}

		return false;
	}

	public ArrayList<City> getOwnedCities() {
		return ownedCities;
	}

	public ArrayList<Unit> getOwnedUnits() {
		return ownedUnits;
	}

	public City getCapitalCity() {
		return ownedCities.get(0);
	}

	public Civ getCiv() {
		return civilization;
	}

	public void setCivilization(CivType civType) {
		this.civilization = civType.getCiv(this);
	}

	public void removeUnit(Unit unit) {
		ownedUnits.remove(unit);
	}

	public ArrayList<Unit> getUnitsOfType(Class<? extends Unit> unitClass) {
		ArrayList<Unit> units = new ArrayList<>();
		for (Unit unit : ownedUnits) {
			if (unit.getClass() == unitClass) {
				units.add(unit);
			}
		}

		return units;
	}

	public void setSpawnPos(int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}

	public boolean hasSpawnPos() {
		return spawnX != -1 && spawnY != -1;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public boolean canCaptureUnit(Unit unit) {
		return civilization.canCaptureUnit(unit);
	}

	public Diplomacy getDiplomacy() {
		return diplomacy;
	}

	public PlayerReligion getReligion() {

		return playerReligion;
	}

	public boolean hasUnitOfType(Class<? extends Unit> unitClass) {
		for (Unit unit : ownedUnits) {
			if (unit.getClass() == unitClass)
				return true;
		}
		return false;
	}
}
