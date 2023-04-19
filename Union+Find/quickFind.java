public class quickFind {
    private int[] id;
    
    public quickFind(int n) {
        this.id = new int[n];
        for (int i = 0; i < n; i++) {
            this.id[i] = i;
        }
    }
    
    public boolean  connected(int p, int q) {
        return this.id[p] == this.id[q];
    }
    
    // does m operations in m*n time (quad)
    public void union(int p, int q) {
        int pid = this.id[p];
        int qid = this.id[q];
        for (int i = 0; i < this.id.length; i++) {
            if (this.id[i] == pid) {
                this.id[i] = qid;
            }
        }
    }
}
