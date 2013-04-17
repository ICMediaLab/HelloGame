package entities.aistates.decisiontree;

import conditiontree.TreeNode;

public abstract class Leaf<T> extends TreeNode<T> {
	
	public abstract void setState(String stateStr);

}
