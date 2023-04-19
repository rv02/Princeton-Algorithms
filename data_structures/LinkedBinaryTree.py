class LinkedBinaryTree(object):
    """Linked representation of a binary tree structure."""

    class _Node:        #Lightweight, nonpublic class for storing a node
        __slots__ = '_element', '_parent', '_left', '_right'
        def __init__(self, element, parent = None, left = None, right = None):
            self._element = element
            self._parent = parent
            self._left = left
            self._right = right
    
    class Position(object):
        """An abstraction representing the location of a single element."""

        def __init__(self, container, node):
            """Constructor should not be invoked by user"""
            self._container = container
            self._node = node 

        def element(self):
            """Return the element sotred at this position"""
            return self._node._element

        def __eq__(self, other):
            """return True if othrt is a Position representing the same location"""
            return type(other) is type(self) and other._node is self._node

    def _validate(self, p):
        """Return associated node, if position is valid.
        A validate utility for robustly checking the validity of a given position instance 
        when unwrapping it"""
        if not isinstance(p, self.Position):
            raise TypeError('p must be proper Position type')
        if p._container is not self:
            raise ValueError('p does not belong to this container')
        if p._node._parent is p._node:      #convention for deprecated nodes
            raise ValueError('p is no longer valid')
        return p._node
    
    def _make_position(self, node):
        """Return Position instance for given node (or None if no node).
        A utility for wrapping a node as a position to return to a caller."""
        return self.Position(self, node) if node is not None else None

    #--------------binary tree constructor--------------
    def __init__(self):
        """Create an initially empty binary tree."""
        self._root = None
        self._size = 0

    #------------------public accessors--------------------
    def __len__(self):
        """Return total no. of elements in the tree."""
        return self._size
    
    def root(self):
        """return the root Position of the tree"""
        return self._make_position(self._root)

    def parent(self, p):
        """Return the Position of p's parent (or None if p is root)"""
        node = self._validate(p)
        return self._make_position(node._parent)

    def left(self, p):
        node = self._validate(p)
        return self._make_position(node._left)

    def right(self, p):
        node = self._validate(p)
        return self._make_position(node._right)

    def num_children(self, p):
        node = self._validate(p)
        count = 0
        if node._left is not None:
            count += 1
        if node._right is not None:
            count += 1
        return count

    def print_tree(self):
        """Print out all tree nodes
        as they are visited in
        a pre-order traversal."""
        return self._inorder_print(self._root, "")[:-1]


    def _add_root(self, e):
        """Place new element e at the root of an empty tree and return new Position.
            Raise ValueError if tree nonempty."""
        if self._root is not None: raise ValueError('Root exists')
        self._size = 1
        self._root = self._Node(e)
        return self._make_position(self._root)

    def _add_left(self, p, e):
        node = self._validate(p)
        if node._left is not None: raise ValueError('Left child exists')
        self._size += 1
        node._left = self._Node(e, node)
        return self._make_position(node._left)

    def _add_right(self, p, e):
        node = self._validate(p)
        if node._right is not None: raise ValueError('Right child exists')
        self._size += 1
        node._right = self._Node(e, node)
        return self._make_position(node._right)

    def _replace(self, p, e):
        node = self.validate(p)
        old = node._element
        node._element = e
        return old
       
    def _delete(self, p):
        """Delete the node at Position p, and replace it with its child, if any.
        Return the elment stored at Position p.
        Raise ValueError if Position p is invalid or p has two children."""
        
        node  = self._validate(p)
        if self.num_children(p) == 2:raise ValueError('p has two children')
        child = node._left if node._left else node._right 
        if child is not None:
            child._parent = node._parent    #child's grandparent becomes parent
        if node is self._root:
            self._root = child 
        else:
            parent = node._parent
            if node is parent._left:
                parent._left  = child
            else:
                parent._right = child
        self._size -= 1
        node._parent = node     # convention for deprecated node 

    def _attach(self, p, t1, t2):
        """Attach trees t1 and t2 as left and right subtrees of external p"""
        node = self._validate(p)
        if self.num_children(p): raise ValueError('position must be leaf')
        # if not self.is_leaf(p) is used here
        if not type(self) is type(t1) is type(t2):   #all trees must be of same type
            raise TypeError('Tree types must match')
        self._size += len(t1) + len(t2)
        if len(t1):    
            t1._root._parent = node
            node._left = t1._root
            t1._root = None         #set t1 instance to empty
            t1._size = 0
        if len(t2):    
            t2._root._parent = node
            node._right = t2._root
            t2._root = None         #set t2 instance to empty
            t2._size = 0 
   

    def _preorder_print(self, start, traversal):
        """Helper method - use this to create a 
        recursive print solution."""
        if start:
            traversal += (str(start._element) + "-")
            traversal = self._preorder_print(start._left, traversal)
            traversal = self._preorder_print(start._right, traversal)
        return traversal


    def _inorder_print(self, start, traversal):
        """Helper method - use this to create a 
        recursive print solution."""
        if start:
            traversal = self._inorder_print(start._left, traversal)
            traversal += (str(start._element) + "-")
            traversal = self._inorder_print(start._right, traversal)
        return traversal

if __name__ == '__main__':
    tree = LinkedBinaryTree()
    tree._add_root(10)
    tree._add_left(tree.root(), 6)
    tree._add_right(tree.root(), 13)
    tree._delete(tree.left(tree.root()))
    tree._add_left(tree.root(), 7)
    t1 = LinkedBinaryTree()
    t2 = LinkedBinaryTree()
    t1._add_root(6)
    tree._attach(tree.left(tree.root()), t1, t2)
    tree._delete(tree.left(tree.root()))
    print(tree.print_tree())