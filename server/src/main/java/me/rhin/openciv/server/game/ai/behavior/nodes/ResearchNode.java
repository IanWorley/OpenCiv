package me.rhin.openciv.server.game.ai.behavior.nodes;

import java.util.ArrayList;

import me.rhin.openciv.server.game.AbstractPlayer;
import me.rhin.openciv.server.game.ai.behavior.BehaviorResult;
import me.rhin.openciv.server.game.ai.behavior.BehaviorStatus;
import me.rhin.openciv.server.game.ai.behavior.PlayerNode;
import me.rhin.openciv.server.game.research.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResearchNode extends PlayerNode {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResearchNode.class);

	public ResearchNode(AbstractPlayer player, String name) {
		super(player, name);
	}

	@Override
	public BehaviorResult tick() {
		ArrayList<Technology> availableTechs = new ArrayList<>();
		for (Technology tech : player.getResearchTree().getTechnologies()) {
			if (tech.canResearch())
				availableTechs.add(tech);
		}

		// Choose top tech based on order of ResearchProperties.
		float techValue = 0;
		Technology topTech = null;
		for (Technology tech : availableTechs) {
			float currentValue = tech.getTechValue();
			if (techValue < currentValue) {
				techValue = currentValue;
				topTech = tech;
			}
		}

		if (topTech == null) {
			// LOGGER.info("FIXME: No techs with properties.");
			return new BehaviorResult(BehaviorStatus.FAILURE, this);
		}

		LOGGER.info("Tech Choose:" + topTech.getName());
		player.getResearchTree().chooseTech(topTech);

		return new BehaviorResult(BehaviorStatus.SUCCESS, this);
	}

}
