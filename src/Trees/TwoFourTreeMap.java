package Trees;

import java.util.Comparator;

import Map.AbstractSortedMap;
import Map.SortedTableMap;
import Trees.TwoFourTree.TwoFourNode;

public class TwoFourTreeMap<K,V> extends AbstractSortedMap<K,V>{
	
	protected TwoFourTree<K,V> tree = new TwoFourTree<>();
	
	public TwoFourTreeMap(Comparator<K> c){
		super(c);
	}
	
	public TwoFourTreeMap(){
		super();
	}
	
	public Position<SortedTableMap<K,V>> treeSearch(Position<SortedTableMap<K,V>> p, K key){
		
		if(isExternal(p)){
			return p;				// key not found, return empty external node
		}
		
		int entriesAmount = ((TwoFourNode<K,V>) p).size();
		
		
	}
}
