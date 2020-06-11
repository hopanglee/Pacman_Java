import java.awt.Rectangle;

public abstract class GameObject extends Rectangle implements RenderableObject {
	private boolean enabled = true;

	public void setEnabled(boolean b) {
		enabled = b;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
