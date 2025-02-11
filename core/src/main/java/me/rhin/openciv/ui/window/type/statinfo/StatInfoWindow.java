package me.rhin.openciv.ui.window.type.statinfo;

import com.badlogic.gdx.Gdx;

import me.rhin.openciv.Civilization;
import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.listener.MouseMoveListener;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.ui.background.ColoredBackground;
import me.rhin.openciv.ui.window.AbstractWindow;

public class StatInfoWindow extends AbstractWindow implements MouseMoveListener {

	private ColoredBackground background;

	private float mouseX, mouseY;

	public StatInfoWindow(float x, float y) {
		setBounds(x, y, 250, 150);
		this.background = new ColoredBackground(TextureEnum.UI_LIGHT_GRAY.sprite(), 0, 0, getWidth(), getHeight());
		addActor(background);

		this.mouseX = Gdx.input.getX();
		this.mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		Civilization.getInstance().getEventManager().addListener(MouseMoveListener.class, this);
	}

	@Override
	public void onClose() {
		super.onClose();
		Civilization.getInstance().getEventManager().clearListenersFromObject(this);
	}

	@Override
	public boolean disablesInput() {
		return false;
	}

	@Override
	public boolean disablesCameraMovement() {
		return false;
	}

	@Override
	public boolean closesOtherWindows() {
		return false;
	}

	@Override
	public boolean closesGameDisplayWindows() {
		return false;
	}

	@Override
	public boolean isGameDisplayWindow() {
		return false;
	}

	@Override
	public void onMouseMove(float x, float y) {
		if (x != mouseX || y != mouseY)
			Civilization.getInstance().getWindowManager().closeWindow(getClass());
	}

}
