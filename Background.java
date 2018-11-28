public class Background {
    private String src;
    private int y1, y2, width, height;	
    public Background() {
    	this.setImageSource("images/background.png");
    	this.width = 2160;
    	this.height = 1713;
    	this.y1 = 0;
    	this.y2 = -1 * this.height;
    }	
    public void moveDown(int units) {
    	this.y1 += units;
    	this.y2 += units;
    	if(this.y1 > this.height) this.y1 = this.y2 - this.height;
    	if(this.y2 > this.height) this.y2 = this.y1 - this.height;
    }	
    public void setImageSource(String src) {
    	this.src = src;
    }
    public String getImageSource() {
    	return this.src;
    }	
    public int getY1() {
    	return this.y1;
    }	
    public int getY2() {
    	return this.y2;
    }	
    public int getWidth() {
    	return this.width;
    }	
    public int getHeight() {
    	return this.height;
    }
}
