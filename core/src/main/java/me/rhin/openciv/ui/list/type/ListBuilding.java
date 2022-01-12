package me.rhin.openciv.ui.list.type;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;

import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.building.Building;
import me.rhin.openciv.game.city.specialist.SpecialistContainer;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.ui.background.ColoredBackground;
import me.rhin.openciv.ui.button.Button;
import me.rhin.openciv.ui.button.type.SpecialistCitizenButton;
import me.rhin.openciv.ui.label.CustomLabel;
import me.rhin.openciv.ui.list.ContainerList;
import me.rhin.openciv.ui.list.ListObject;

public class ListBuilding extends ListObject {

	private Building building;
	private Sprite backgroundSprite;
	private ColoredBackground buildingSprite;
	private CustomLabel buildingNameLabel;
	private ArrayList<Sprite> statIcons;
	private ArrayList<CustomLabel> statLabels;
	private ArrayList<Button> specialistButtons;

	public ListBuilding(Building building, ContainerList containerList, float width, float height) {
		super(width, height, containerList, "Building");

		this.building = building;
		backgroundSprite = TextureEnum.UI_GRAY.sprite();
		backgroundSprite.setSize(width, height);
		buildingNameLabel = new CustomLabel(building.getName());
		buildingNameLabel.setSize(width, height);
		buildingNameLabel.setAlignment(Align.topLeft);

		// FIXME: We need to convert our sprites to actors like this
		buildingSprite = new ColoredBackground(building.getTexture().sprite(), getWidth() - 34,
				getHeight() / 2 - 32 / 2, 32, 32);
		addActor(buildingSprite);

		this.statIcons = new ArrayList<>();
		this.statLabels = new ArrayList<>();
		this.specialistButtons = new ArrayList<>();

		for (Stat stat : building.getStatLine().getStatValues().keySet()) {

			String name = null;
			if (stat.name().contains("_")) {
				name = stat.name().substring(0, stat.name().indexOf('_')).toUpperCase();
			} else {
				name = stat.name().toUpperCase();
			}

			Sprite sprite = TextureEnum.valueOf("ICON_" + name).sprite();
			sprite.setSize(16, 16);
			statIcons.add(sprite);

			CustomLabel label = new CustomLabel("+" + (int) building.getStatLine().getStatValue(stat));
			if (stat == Stat.MAINTENANCE)
				label.setColor(new Color(66, 0, 0, 1));
			statLabels.add(label);
		}

		if (building instanceof SpecialistContainer) {
			SpecialistContainer specialistContinaer = (SpecialistContainer) building;
			for (int i = 0; i < specialistContinaer.getSpecialistSlots(); i++) {
				specialistContinaer.getSpecialistType();
				specialistButtons
						.add(new SpecialistCitizenButton(building.getCity(), specialistContinaer, 0, 0, 32, 32));
			}
		}
	}
	
	@Override
	protected void onClicked(InputEvent event) {
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		backgroundSprite.draw(batch);
		buildingNameLabel.draw(batch, parentAlpha);

		for (Sprite sprite : statIcons)
			sprite.draw(batch);

		for (CustomLabel label : statLabels)
			label.draw(batch, parentAlpha);

		super.draw(batch, parentAlpha);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		backgroundSprite.setPosition(x, y);
		buildingNameLabel.setPosition(x, y);
		buildingSprite.setPosition(getWidth() - 34, getHeight() / 2 - 32 / 2);

		float originX = x;
		for (int i = 0; i < statIcons.size(); i++) {
			Sprite sprite = statIcons.get(i);
			CustomLabel label = statLabels.get(i);
			sprite.setPosition(originX, y);
			originX += sprite.getWidth();
			label.setPosition(originX, y + label.getHeight() / 2);
			originX += label.getWidth() + 4;
		}
	}

	@Override
	public int compareTo(ListObject listObj) {
		ListBuilding listBuilding = (ListBuilding) listObj;
		return Integer.compare((int) building.getProductionCost(),
				(int) listBuilding.getBuilding().getProductionCost());
	}

	private Building getBuilding() {
		return building;
	}
}
