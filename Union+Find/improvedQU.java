// Weighted with Path-compression
public class improvedQU {
	private int[] id;
	private int[] sz;

	public improvedQU(int N) {
		this.id = new int[N];
		this.sz = new int[N];
		for (int i = 0; i < N; i++) {
			this.id[i] = i;
			this.sz[i] = 1;
		}
	}

	public int root(int n) {
		while (this.id[n] != n) {
			this.id[n] = this.id[this.id[n]];		// path-compression by halving
			n = this.id[n];
		}
		return n;
	}

	public boolean connected(int p, int q) {
		return this.root(p) == this.root(q);
	}

	public void union(int p, int q) {
		int i = this.root(p);
		int j = this.root(q);
		if (i == j) {
			return;
		} 
		// make smaller root point to larger one
		if (this.sz[i] < this.sz[j]) {
			this.id[i] = j;
			this.sz[j] += this.sz[i];
		} else {
			this.id[j] = i;
			this.sz[i] += this.sz[j];
		}
	}
}