package me.rhin.openciv.ui.list;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

import me.rhin.openciv.asset.TextureEnum;

public abstract class ListObject extends Group implements Comparable<ListObject> {

	private String key;
	private Sprite seperatorSprite;
	protected ContainerList containerList;

	public ListObject(float width, float height, ContainerList containerList, String key) {
		this.setSize(width, height);
		this.key = key;

		this.seperatorSprite = TextureEnum.UI_BLACK.sprite();
		seperatorSprite.setSize(width, 1);

		this.containerList = containerList;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		seperatorSprite.draw(batch);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		seperatorSprite.setPosition(x, y);
	}

	@Override
	public int compareTo(ListObject listObj) {
		return key.compareTo(listObj.getKey());
	}

	public boolean inContainerListBounds(float x, float y) {
		if (x > containerList.getX() && x < (containerList.getX() + containerList.getWidth()))
			if (y > containerList.getY() && y < (containerList.getY() + containerList.getHeight()))
				return true;

		//FIXME: Handle this.
		return true;
	}

	public String getKey() {
		return key;
	}
}
