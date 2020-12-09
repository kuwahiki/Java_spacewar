package game;

public class mylogger{
	boolean a = true,b = true;
	public mylogger() {
	}
	public void OutsideEnemy() {
		System.out.println("敵が外に出ました");
	}
	public void ApperaEnemy(int pnum){
		System.out.println("敵" + pnum + "が出現しました");
	}
	public void ApeeraBoss(){
		System.out.println("ボスが出現しました");
	}
	public void GameStart(){
		if(b) {
		System.out.println("GameStart");
		b = false;
		}
	}
	public void GameClear(boolean frist){
		if(frist) 
		System.out.println("GameClear");
		
	}
	public void GameOver(){
		if(a) {
		System.out.println("GameOver");
		a = false;
		}
	}
	public void DieEnemy(boolean i) {
		if(i = true)
			System.out.println("敵を倒しました");
	}
	public void DieBoss() {
		System.out.println("ボスを倒しました");
	}
}
