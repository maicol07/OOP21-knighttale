package it.unibo.aknightstale.entity.view;

import javafx.scene.image.Image;

public class EntityViewImpl implements EntityView {
	
	protected Image image;

	public EntityViewImpl(Image image) {
		super();
		this.image = image;
	}

	@Override
	public Image getImage() {
		return this.image;
	}

}