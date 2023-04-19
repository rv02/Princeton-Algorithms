public class quickUnion {
	private int[] id;

	public quickUnion(int N) {
		this.id = new int[N];
		for (int i = 0; i < N; i++) {
			this.id[i] = i;
		}
	}

	public int root(int n) {
		while (this.id[n] != n) {
			n = this.id[n];
		}
		return n;
	}

	// linear 
	public boolean connected(int p, int q) {
		return this.root(p) == this.root(q);
	}

	public void union(int p, int q) {
		id[this.root(p)] = this.root(q);
	}
}