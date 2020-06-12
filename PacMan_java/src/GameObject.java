import java.awt.Rectangle;

// 2019311680 김정원 작성
public abstract class GameObject extends Rectangle implements RenderableObject {

	private boolean enabled = true;

	public void setEnabled(boolean b) {
		enabled = b;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
