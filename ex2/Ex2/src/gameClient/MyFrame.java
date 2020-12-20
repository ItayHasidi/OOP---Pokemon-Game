package gameClient;
import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JComponent {
	private int _ind;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Image im;
	private Graphics g2;
//	private
//	private ImageIcon ash =
//	private

//	private

//	public JLabel ashLabel;
//	public JLabel pikachuLabel;
//	public JLabel bulbassaurLabel;


	MyFrame(String longString) throws IOException {
//		super((longString));
		setPreferredSize(new Dimension(975,425));
		int _ind = 0;
	}

	MyFrame() throws IOException {
//		super((longString));
		setPreferredSize(new Dimension(975,425));
		int _ind = 0;
	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}
	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}

	/**
	 * based on a video tutorial - https://www.youtube.com/watch?v=YDNKyhYeIZA
	 * @param g
	 */
	public void paint2(Graphics g) throws IOException {
		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
		drawPokemons(g);
		drawGraph(g);
		drawAgents(g);
		drawInfo(g);
	}

	/**
	 * based on a video tutorial - https://www.youtube.com/watch?v=YDNKyhYeIZA
	 * @param g
	 */
	public void paint(Graphics g){
		super.paint(g);
		im = createImage(getWidth(), getHeight());
		g2 = im.getGraphics();
		try {
			paint2(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(im,0,0,this);
	}

	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
	}
	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}
	private void drawPokemons(Graphics g) throws IOException {
//		BufferedImage pikachu = ImageIO.read(new File("data/pikachu.png"));
//		BufferedImage bulbassaur = ImageIO.read(new File("data/bulbassaur.png")) ;
//		ImageIcon pikachu = new ImageIcon("data/pikachu.png");
//		ImageIcon bulbassaur = new ImageIcon("data/bulbassaur.png");
//
//		pikachuLabel = new JLabel(pikachu);
//		bulbassaurLabel = new JLabel(bulbassaur);
//
////		pikachuLabel = new JLabel(new ImageIcon(pikachu));
////		bulbassaurLabel = new JLabel(new ImageIcon(bulbassaur));
//
//		add(pikachuLabel);
//		add(bulbassaurLabel);

		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
		Iterator<CL_Pokemon> itr = fs.iterator();
		while(itr.hasNext()) {
			CL_Pokemon f = itr.next();
			Point3D c = f.getLocation();
//			bulbassaurLabel.setBounds((int)c.x(),(int)c.y(),15,15);
			int r=10;
			g.setColor(Color.green);
			if(f.getType()<0) {
//				pikachuLabel.setBounds((int)c.x(),(int)c.y(),15,15);
				g.setColor(Color.orange);
			}
			if(c!=null) {
				geo_location fp = this._w2f.world2frame(c);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
			//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
			}
		}
		}
	}
	private void drawAgents(Graphics g) throws IOException {
//		BufferedImage ash = ImageIO.read(new File("data/ash.png")) ;
//		ImageIcon ash = new ImageIcon("data/ash.png");
//
//		ashLabel = new JLabel(ash);
//
////		ashLabel = new JLabel(new ImageIcon(ash));
//
//		add(ashLabel);

		List<CL_Agent> rs = _ar.getAgents();
	//	Iterator<OOP_Point3D> itr = rs.iterator();
		g.setColor(Color.red);

		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
//			ashLabel.setBounds((int)c.x(), (int)c.y(),15,15 );
			int r=8;
			i++;
			if(c!=null) {
				geo_location fp = this._w2f.world2frame(c);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
			}
		}
	}
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}
}
