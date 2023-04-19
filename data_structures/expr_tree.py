from LinkedBinaryTree import LinkedBinaryTree

class ExpressionTree(LinkedBinaryTree):
	
	def __init__(self, token, left = None, right = None):
		super().__init__()
		if not isinstance(token, str):
			raise TypeError('Token must be a string')
		self._add_root(token)
		if left is not None:
			if token not in '+-*x/':
				raise ValueError('token must be valid operator')
			self._attach(self.root(), left, right)

	def __str__(self):
			pieces = []
			self._parenthesize_recur(self.root(), pieces)
			return ''.join(pieces)

	def _parenthesize_recur(self, p, result):
		if self.num_children(p) == 0:
			result.append(str(p.element()))
		else:
			result.append('(')
			self._parenthesize_recur(self.left(p), result)
			result.append(p.element( ))
			self._parenthesize_recur(self.right(p), result)
			result.append(')')

	def evaluate(self):
		return self._evaluate_recur(self.root())

	def _evaluate_recur(self, p):
		if self.num_children(p) == 0:
			return float(p.element())
		else:
			op = p.element()
			left_val = self._evaluate_recur(self.left(p))
			right_val = self._evaluate_recur(self.right(p))
			if op == '+': return left_val + right_val
			elif op == '-': return left_val - right_val
			elif op == '/': return left_val / right_val
			else: return left_val * right_val

def build_expression_tree(tokens):
	S = []
	for t in tokens:
		if t in '+-x*/':
			S.append(t)
		elif t not in '()':
			S.append(ExpressionTree(t))
		elif t == ')':
			right = S.pop()
			op = S.pop()
			left = S.pop()
			S.append(ExpressionTree(op, left, right))
	return S.pop()

	
if __name__ == '__main__':
	exp = "(((3+1)x4)/((9-5)+2))"
	tree = build_expression_tree(exp)
	print(str(tree))
	print(tree.evaluate())