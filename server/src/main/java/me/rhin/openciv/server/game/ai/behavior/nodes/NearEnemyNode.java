package me.rhin.openciv.server.game.ai.behavior.nodes;

import me.rhin.openciv.server.game.ai.behavior.BehaviorStatus;
import me.rhin.openciv.server.game.ai.behavior.ConditionNode;
import me.rhin.openciv.server.game.map.tile.Tile;
import me.rhin.openciv.server.game.unit.Unit;

public class NearEnemyNode extends ConditionNode {

	private Unit unit;

	public NearEnemyNode(Unit unit) {
		super("NearEnemyNode");
		this.unit = unit;
	}

	@Override
	public void tick() {
		// FIXME: Improve this. Reference other units in a 5 tile radius.
		for (Tile tile : unit.getObservedTiles()) {
			if (tile.getTopUnit() != null
					&& unit.getPlayerOwner().getDiplomacy().atWar(tile.getTopUnit().getPlayerOwner())) {

				setStatus(BehaviorStatus.SUCCESS);
				super.tick();
				return;
			}
		}

		setStatus(BehaviorStatus.FAILURE);
	}

}
