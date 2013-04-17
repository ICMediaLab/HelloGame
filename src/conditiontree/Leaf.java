package conditiontree;


public abstract class Leaf<T> extends TreeNode<T> {
	
	protected abstract void setState(String stateStr);

}
