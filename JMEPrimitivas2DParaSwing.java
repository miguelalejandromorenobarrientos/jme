package swing_primitivas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jme.Expresion;
import jme.ExpresionThread;
import jme.abstractas.Token;
import jme.script.Script;
import jme.script.ScriptException;
import jme.script.ctx2d.AbstractPrimitivas2D;
import jme.script.ctx2d.Primitivas2DAdapter;
import jme.terminales.Booleano;
import jme.terminales.Texto;

/**
 * Implementación básica de {@link AbstractPrimitivas2D} para Swing. 
 * @author Miguel Alejandro Moreno Barrientos, (C)2021,2022
 * @since v0.1.0, JME v0.6.2.0, JMEScript 0.1.3.0
 * @version v0.1.1, JME v0.6.2.3, JMEScript v0.2.1
 */
public class JMEPrimitivas2DParaSwing extends Primitivas2DAdapter 
{
	private Graphics2D g2;
	
	/** máximo tiempo de ejecución de las expresiones en la entrada (ms) */
	public long maxTiempoDeEvaluacion = 4000L;
	
	public static JFileChooser fileChooser;
	
	private List<AbstractKeyStroke> strokeList = 
							Collections.synchronizedList( new LinkedList<AbstractKeyStroke>() );
	private List<AbstractRatonEvent> ratonEventList = 
							Collections.synchronizedList( new LinkedList<AbstractRatonEvent>() );
	
	protected Color colorRelleno = Color.BLACK;  // color de relleno en figuras
	
	public JMEPrimitivas2DParaSwing() {}
	
	public JMEPrimitivas2DParaSwing( Graphics2D g2 ) 
	{
		this();
		this.g2 = g2;
	}
	
	public Graphics2D getGraphics() { return g2; }	
	public void setGraphics( Graphics2D g2 ) { this.g2 = g2; }
	
	@Override
	public String getUIName() { return "Swing"; }
	
	@Override
	public String getUIVersion() 
	{ 
		try
		{
			return System.getProperty("java.version");
		}
		catch ( SecurityException e )
		{
			return "?";
		}
	}
	
	@Override
	public String getPrim2DName() { return "Implementacion Basica de Primitivas2D para SWING"; }
	
	@Override
	public String getPrim2DVersionName() { return "Cuarentena"; }
	
	@Override
	public String getPrim2DVersion() { return "0.1.0"; }
	
	@Override
	public int getAnchoPantalla() { return Toolkit.getDefaultToolkit().getScreenSize().width; }
	
	@Override
	public int getAltoPantalla() { return Toolkit.getDefaultToolkit().getScreenSize().height; }

	@Override
	public void mostrarMensaje( String msj, MSJ tipo, Integer posX, Integer posY, Integer tiempo ) 
	{
		int msjTipo;
		switch( tipo )
		{
			case WARNING: msjTipo = JOptionPane.WARNING_MESSAGE; break;
			case ERROR: msjTipo = JOptionPane.ERROR_MESSAGE; break;
			case QUESTION: msjTipo = JOptionPane.QUESTION_MESSAGE; break;
			case PLAIN: msjTipo = JOptionPane.PLAIN_MESSAGE; break;
			default: msjTipo = JOptionPane.INFORMATION_MESSAGE;
		}
		JOptionPane.showConfirmDialog( getPadre() instanceof Component 
									   ? (Component) getPadre() 
									   : null, 
									   msj, 
									   "Script mensaje:", 
									   JOptionPane.OK_CANCEL_OPTION, 
									   msjTipo ); 
	}
	
	@Override
	public void entradaUsuario( Script script, ENTRADA entrada, String varname, String msj, 
								String[] opciones ) 
	{
		final Map<String,Token> scriptVars = script.getVarMap();
		final Component padre = getPadre() instanceof Component ? (Component) getPadre() : null;
		
		switch ( entrada )
		{
			default:
			// solicitud de texto
			case TEXTO:
			{
				String txt = JOptionPane.showInputDialog( padre, msj, 
												String.format( "Solicitud de <%s> JME para '%s'", 
														Texto.class.getSimpleName(), varname ),
												JOptionPane.QUESTION_MESSAGE );
				if ( txt == null )  // entrada cancelada
					txt = Expresion.ERROR.__error1__.name();
				// establecer variable en script
				scriptVars.put( varname, new Texto( txt ) ); 
				break;
			}
			// solicitud de expresión
			case EXPRESION:    
			{
				final String exptxt = JOptionPane.showInputDialog( padre, msj, 
												String.format( "Solicitud de <%s> JME para '%s'", 
														Expresion.class.getSimpleName(), varname ),
												JOptionPane.QUESTION_MESSAGE ); 
				if ( exptxt == null )  // entrada cancelada
				{
					scriptVars.put( varname, new Texto( Expresion.ERROR.__error1__.name() ) );
					break;
				}

				final ExpresionThread expThread;
				try
				{
					// crear expresión
					final Expresion exp = new Expresion( exptxt )
									.setVariables( new HashMap<String,Token>( script.getVarMap() ) );
					// crear hilo y ejecutar
					expThread = new ExpresionThread( exp, maxTiempoDeEvaluacion );
					expThread.startAndJoin();
				}
				catch ( Throwable e )
				{
					scriptVars.put( varname, new Texto( Expresion.ERROR.__error2__.name() ) );
					break;
				}
				// establecer variable en script
				scriptVars.put( varname, expThread.getResultado() ); 
				break;
			}
			// solicitud de confirmación (booleano)
			case BOOLEANO:
			{
				// seleccionar SI/NO
				final int opcion = JOptionPane.showConfirmDialog( padre, msj, 
										String.format( "Solicitud de <%s> para '%s'", 
													   Booleano.class.getSimpleName(), varname ),
										JOptionPane.YES_NO_OPTION ); 
				if ( opcion == JOptionPane.CLOSED_OPTION )  // entrada cancelada
					scriptVars.put( varname, new Texto( Expresion.ERROR.__error1__.name() ) );
				else  // establecer variable en script
					scriptVars.put( varname, Booleano.booleano( opcion == JOptionPane.YES_OPTION ) ); 
				break;
			}
			// selección de opciones
			case OPCIONES:
			{
				// obtener índice de la opción
				final int opcion = JOptionPane.showOptionDialog( padre, msj, 
						"Opciones para '" + varname + "'", 
						JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null );
				if ( opcion == JOptionPane.CLOSED_OPTION )  // entrada cancelada
					scriptVars.put( varname, new Texto( Expresion.ERROR.__error1__.name() ) );
				else  // establecer variable en script
					scriptVars.put( varname, new Texto( opciones[opcion] ) ); 
				break;
			}
		}		
	}
	
	/**
	 * {@inheritDoc}
	 * <br><br>
	 * <i>Nota: <b>aunque sea sincronizada, es necesario acceder en bloque, o se obtendrá en 
	 * ocasiones un error de concurrencia<b></i> 
	 */
	@Override	
	public List<AbstractKeyStroke> getStrokeList() { return strokeList; }
	
	/**
	 * {@inheritDoc}
	 * <br><br>
	 * <i>Nota: <b>aunque sea sincronizada, es necesario acceder en bloque, o se obtendrá en 
	 * ocasiones un error de concurrencia<b></i> 
	 */
	@Override
	public List<AbstractRatonEvent> getRatonEventList() { return ratonEventList; }

	@Override
	public File[] seleccionarArchivos( String path, String titulo ) 
	{
		fileChooser = fileChooser != null ? fileChooser : new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
		fileChooser.setCurrentDirectory( path != null ? new File(path) : null );
		fileChooser.setDialogTitle( titulo );
		fileChooser.updateUI();
		switch( fileChooser.showOpenDialog( getPadre() instanceof Component 
											? (Component) getPadre() 
											: null ) )
		{
			case JFileChooser.APPROVE_OPTION: return fileChooser.getSelectedFiles();
			case JFileChooser.CANCEL_OPTION: return new File[0];
			case JFileChooser.ERROR_OPTION:
			default: return null;
		}		
	}
	
	@Override
	public void limpiar( int x, int y, int ancho, int alto, AbstractColor cfondo ) 
	{
		// guardar color y matriz originales
		final AffineTransform oriMatriz = g2.getTransform();
		final Color oriColor = g2.getColor();
		// establecer matriz identidad
		g2.setTransform( new AffineTransform() );
		// establecer color de fondo
		if ( cfondo != null )
			g2.setColor( new Color( cfondo.red(), cfondo.green(), cfondo.blue(), cfondo.alpha() ) );
		else  if ( g2.getPaint() instanceof Color )
			g2.setColor( g2.getBackground() );
		// dibujar rectángulo de fondo
		g2.fill( new Rectangle2D.Double( x, y, ancho, alto ) );
		// recuperar color y matriz originales
		g2.setColor( oriColor );			
		g2.setTransform( oriMatriz );
	}
	
	@Override
	public AbstractColor getColorFactory() { return new JSColor(); }
	
	public static class JSColor extends AbstractColor
	{
		private static final long serialVersionUID = 1L;
		
		protected Color color = Color.BLACK;
		
		public JSColor() {}
		public JSColor( Color color ) { this.color = color; }
		public JSColor( int r, int g, int b, int a ) { color = new Color( r, g, b, a ); }
		public JSColor( int r, int g, int b ) { this( r, g, b, 255 ); }
		public JSColor( int intcolor ) { color = new Color( intcolor, true ); }
		public JSColor( String stringcolor ) { color = Color.decode( stringcolor ); }

		@Override
		public JSColor nuevaInstancia( int r, int g, int b, int a ) 
		{
			return new JSColor( r, g, b, a );
		}
		
		@Override
		public JSColor nuevaInstancia( int r, int g, int b ) 
		{
			return new JSColor( r, g, b );
		}
		
		@Override
		public JSColor nuevaInstancia( String stringcolor ) 
		{
			return new JSColor( stringcolor );
		}
		
		@Override
		public JSColor nuevaInstancia( int intcolor ) 
		{
			return new JSColor( intcolor );
		}
		
		@Override
		public int red() { return color.getRed(); }
		
		@Override
		public int green() { return color.getGreen(); }
		
		@Override
		public int blue() { return color.getBlue(); }

		@Override
		public int alpha() { return color.getAlpha(); }

	}  // class JSColor
	

	/////////////////////////
	// PRIMITIVAS GRÁFICAS
	/////////////////////////
	
	@Override
	public void setColor( AbstractColor color ) 
	{
		g2.setColor( new Color( color.red(), color.green(), color.blue(), color.alpha() ) );
	}
	
	@Override
	public AbstractColor getColor() 
	{
		return esContextoIniciado() ? new JSColor( g2.getColor() ) : new JSColor();
	}
	
	@Override
	public void setRelleno( AbstractColor color ) 
	{
		// eliminar gradiente si se modifica el relleno
		if ( !(g2.getPaint() instanceof Color) )
			g2.setPaint( colorRelleno );
		
		colorRelleno = new Color( color.red(), color.green(), color.blue(), color.alpha() );
	}
	
	@Override
	public AbstractColor getRelleno() 
	{
		return esContextoIniciado() ? new JSColor( colorRelleno ) : new JSColor();
	}
	
	@Override
	public void setFondo( AbstractColor color ) 
	{
		final Color c = new Color( color.red(), color.green(), color.blue(), color.alpha() );

		// eliminar gradiente si se modifica el fondo
		if ( !(g2.getPaint() instanceof Color) )
			g2.setPaint( c );
		
		g2.setBackground( c );
	}
	
	@Override
	public AbstractColor getFondo() 
	{
		return esContextoIniciado() ? new JSColor( g2.getBackground() ) : new JSColor();
	}
	
	@Override
	public double[] getMatriz( double[] storeMatrizTransformacion ) 
	{
		if ( !esContextoIniciado() )
			return null;
		
		getGraphics().getTransform().getMatrix(storeMatrizTransformacion);
		
		return storeMatrizTransformacion;
	}
	
	@Override
	public void setMatriz( double[] matrizTransformacion ) 
	{
		getGraphics().setTransform( new AffineTransform( matrizTransformacion ) );
	}

	@Override
	public void setTrazo( float grosor )
	{
		getGraphics().setStroke( 
						 new BasicStroke( grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
	}
	
	@Override
	public void setTrazo( float grosor, CAP extremo, JOIN union, Float inglete, float[] raya, 
						  Float faseRaya )
	{
		final BasicStroke oldStroke = g2.getStroke() instanceof BasicStroke 
									  ? (BasicStroke) g2.getStroke() 
									  : new BasicStroke();
									
	    int awtExtremo = ((BasicStroke) g2.getStroke()).getEndCap();
		if ( extremo != null )
		{
			switch ( extremo )
			{
				case REDONDO: awtExtremo = BasicStroke.CAP_ROUND; break;
				case TOPE: awtExtremo = BasicStroke.CAP_BUTT; break;
				case CUADRADO: awtExtremo = BasicStroke.CAP_SQUARE; break;
				default:
			}
		}
		
	    int awtUnion = ((BasicStroke) g2.getStroke()).getLineJoin();
		if ( union != null )
		{
			switch ( union )
			{
				case BISEL: awtUnion = BasicStroke.JOIN_BEVEL; break;
				case INGLETE: awtUnion = BasicStroke.JOIN_MITER; break;
				case REDONDO: awtUnion = BasicStroke.JOIN_ROUND; break;
				default:
			}
		}		
		g2.setStroke( new BasicStroke( grosor, 
									   awtExtremo, 
									   awtUnion, 
									   inglete != null ? inglete : oldStroke.getMiterLimit(), 
									   raya != null 
									   ? (raya.length == 1 && raya[0] == 0f ? null : raya) 
									   : oldStroke.getDashArray(), 
									   faseRaya != null ? faseRaya : oldStroke.getDashPhase() ) );
	}
	
	@Override
	public void setGradiente( double x1, double y1, double x2, double y2, Double radio, 
							  float[] fracciones, AbstractColor[] colores, CICLO ciclo, 
							  double[] transform ) 
	{
		// convertir ciclo desde especificación a AWT
		final CycleMethod metCiclo;
		switch ( ciclo )
		{
			default:
			case CICLO: metCiclo = CycleMethod.REPEAT; break;
			case REFLEJO: metCiclo = CycleMethod.REFLECT; break;
			case NO_CICLO: metCiclo = CycleMethod.NO_CYCLE; break;
		}
		// convertir colores
		final Color[] awtColores = new Color[colores.length];
		for ( int i = 0; i < colores.length; i++ )
			awtColores[i] = new Color( colores[i].red(), colores[i].green(), 
									   colores[i].blue(), colores[i].alpha() );
		// transformación
		final AffineTransform at = transform == null 
								   ? new AffineTransform() 
								   : new AffineTransform( transform );
		// gradiente lineal
		if ( radio == null )
		{
			final LinearGradientPaint lgrad = new LinearGradientPaint( 
						new Point2D.Double( x1, y1 ), new Point2D.Double( x2, y2 ), 
						fracciones, awtColores, metCiclo, ColorSpaceType.LINEAR_RGB, at );
			g2.setPaint( lgrad );
		}
		// gradiente radial
		else
		{
			final RadialGradientPaint rgrad = new RadialGradientPaint( 
					new Point2D.Double( x1, y1 ), radio.floatValue(), new Point2D.Double( x2, y2 ), 
					fracciones, awtColores, metCiclo, ColorSpaceType.LINEAR_RGB, at );
			g2.setPaint( rgrad );					
		}
	}
	
	@Override
	public void punto( double x, double y, PUNTO tipo ) 
	{
		switch ( tipo )
		{
			default:
			case PIXEL: g2.draw( new Line2D.Double( x, y, x, y ) ); break;
			case ROMBO: 
			{
				final float grosor = ((BasicStroke) g2.getStroke()).getLineWidth();
				final double[] valoresX = { x, x + grosor, x, x - grosor },
							   valoresY = { y + grosor, y, y - grosor, y };				
				final Path2D poly = new Path2D.Double();				

				poly.moveTo( valoresX[0], valoresY[0] );
				for( int i = 1; i < valoresX.length; i++ )
				   poly.lineTo(valoresX[i], valoresY[i]);
				poly.closePath();
				
				g2.fill( poly );
				break;
			}
			case CRUZ: 
			{
				final float grosor = ((BasicStroke) g2.getStroke()).getLineWidth();
				g2.draw( new Line2D.Double( x - grosor, y, x + grosor, y ) );
				g2.draw( new Line2D.Double( x, y - grosor, x, y + grosor ) );
				break;
			}
			case MIRA: 
			{
				final float grosor = ((BasicStroke) g2.getStroke()).getLineWidth(),
							grosor2 = grosor*3/2f;
				g2.draw( new Line2D.Double( x, y + grosor2, x, y + grosor*2 ) );
				g2.draw( new Line2D.Double( x, y - grosor2, x, y - grosor*2 ) );
				g2.draw( new Line2D.Double( x + grosor2, y, x + grosor*2, y ) );
				g2.draw( new Line2D.Double( x - grosor2, y, x - grosor*2, y ) );
				break;
			}
		}
	}
	
	@Override
	public void segmento( double x1, double y1, double x2, double y2 ) 
	{
		g2.draw( new Line2D.Double( x1, y1, x2, y2 ) );
	}

	/**
	 * {@inheritDoc}<br><br>
	 * <b>Nota:</b> <i>los rectángulos 3D no aceptan valores flotantes, por lo que serán redondeados 
	 * a enteros. Las coordenadas normalizadas a rangos pequeños como [0,1] no funcionarán 
	 * correctamente</i>.<br>
	 * <b>Nota2:</b> <i>No dibuja rectángulos redondeados 3D</i>.
	 */
	@Override
	public void rectangulo( double x1, double y1, double x2, double y2, double anchoArco, 
							double altoArco, boolean relleno, boolean borde, RECT3D rect3d ) 
	{
		final double ancho = x2 - x1, 
					 alto = y2 - y1;		
		final Shape rect;
		
		// rectángulo recto o 3D
		if ( anchoArco <= 0 && altoArco <= 0 )
			rect = rect3d == RECT3D.NINGUNO ? new Rectangle2D.Double( x1, y1, ancho, alto ) : null;
		// rectángulo redondeado
		else
			rect = new RoundRectangle2D.Double( x1, y1, ancho, alto, anchoArco, altoArco );
		
		// relleno
		if ( relleno )
		{
			// color
			if ( g2.getPaint() instanceof Color || rect == null )
			{
				final Color oriColor = g2.getColor();
				g2.setColor( colorRelleno );
				if ( rect != null )
					g2.fill( rect );
				else
					g2.fill3DRect( (int) Math.round(x1), (int) Math.round(y1), 
								   (int) Math.round(ancho), (int) Math.round(alto), 
								   rect3d == RECT3D.RESALTADO );
				g2.setColor( oriColor );
			}
			// gradiente
			else
				g2.fill( rect );
		}
		// borde
		if ( /*!relleno || */borde )
		{
			if ( rect != null )
				g2.draw( rect );
			else
				g2.draw3DRect( (int) Math.round(x1), (int) Math.round(y1), 
							   (int) Math.round(ancho), (int) Math.round(alto), 
							   rect3d == RECT3D.RESALTADO );
		}
	}
	
	@Override
	public void circunferencia( double cx, double cy, double radio, double ang, double arco, 
								CIERRE tipo, boolean relleno, boolean borde )
	{
		final Shape circ;		

		// circunferencia
		if ( arco >= 360. )
			circ = new Ellipse2D.Double( cx-radio, cy-radio, 2*radio, 2*radio );
		// arco
		else
		{
			final int cierre;
			switch ( tipo )
			{
				default:
				case ABIERTO: cierre = Arc2D.OPEN; break;
				case TARTA: cierre = Arc2D.PIE; break;
				case CUERDA: cierre = Arc2D.CHORD;
			}
			circ = new Arc2D.Double( cx-radio, cy-radio, 2*radio, 2*radio, -ang, -arco, cierre );
		}
		
		drawOrFill( circ, relleno, borde );
	}

	@Override
	public void elipse( double x1, double y1, double x2, double y2, boolean relleno, boolean borde ) 
	{
		drawOrFill( new Ellipse2D.Double( x1, y1, x2 - x1, y2 - y1 ), relleno, borde );
	}

	@Override
	public void poligono( double[] x, double[] y, boolean cerrar, boolean relleno, boolean borde ) 
	{
		if ( x.length != y.length )
			throw new ScriptException( 
								"Las coordenadas del poligono/polilinea son de distinto tamano" );
		
		final Path2D poly = new Path2D.Double();				

		poly.moveTo( x[0], y[0] );
		for( int i = 1; i < x.length; i++ )
		   poly.lineTo( x[i], y[i] );
		if ( cerrar )
			poly.closePath();		

		drawOrFill( poly, relleno, borde );
	}
	
	@Override
	public void ruta( AccionPuntos[] accionPuntos, RUTA regla, boolean relleno, boolean borde ) 
	{
		final Path2D.Double path = new Path2D.Double( 
							regla == RUTA.NO_CERO ? Path2D.WIND_NON_ZERO : Path2D.WIND_EVEN_ODD );
		
		for ( AccionPuntos ap : accionPuntos )
		{
			switch ( ap.accion )
			{
				case MOVER_A: path.moveTo( ap.puntos[0][0], ap.puntos[0][1] ); break;
				case LINEA_A: path.lineTo( ap.puntos[0][0], ap.puntos[0][1] ); break;
				case CURVA_A: path.curveTo( ap.puntos[0][0], ap.puntos[0][1],
											 ap.puntos[1][0], ap.puntos[1][1],
											 ap.puntos[2][0], ap.puntos[2][1] ); break;
				case CUAD_A: path.quadTo( ap.puntos[0][0], ap.puntos[0][1],
						 				   ap.puntos[1][0], ap.puntos[1][1] ); break;
				case CERRAR: path.closePath(); break;
				default: throw new IllegalArgumentException( ap.accion.toString() );
			}
		}
		
		drawOrFill( path, relleno, borde );
	}
	
	private void drawOrFill( Shape figura, boolean relleno, boolean borde )
	{
		if ( relleno )
		{
			// color
			if ( g2.getPaint() instanceof Color )
			{
				final Color oriColor = g2.getColor();
				g2.setColor( colorRelleno );
				g2.fill( figura );
				g2.setColor( oriColor );
			}
			// gradiente
			else
				g2.fill( figura );
		}
		if ( /*!relleno ||*/ borde )
			g2.draw( figura );
	}
	
	@Override
	public void texto( String texto, int posX, int posY, int tama, int sombraX, int sombraY, 
					   String familia, ESTILO estilo ) 
	{
		final int awtEstilo;
		switch (estilo)
		{
			case PLANO: awtEstilo = Font.PLAIN; break;
			case NEGRITA: awtEstilo = Font.BOLD; break;
			case CURSIVA: awtEstilo = Font.ITALIC; break;
			case NEG_CUR: awtEstilo = Font.ITALIC|Font.BOLD; break;
	
			default: awtEstilo = Font.PLAIN;
		}
		
		final Font oriFont = g2.getFont(), nuevaFont = new Font( familia, awtEstilo, tama );
		
		g2.setFont( nuevaFont );
		final FontMetrics fm = g2.getFontMetrics( nuevaFont );
		if ( sombraX != 0 || sombraY != 0 )
		{
			final Color oriColor = g2.getColor();
			g2.setColor( colorRelleno );
			g2.drawString( texto, posX + sombraX, posY + sombraY + fm.getHeight() - fm.getDescent() /*- fm.getAscent()*/ );
			g2.setColor( oriColor );
		}
		g2.drawString( texto, posX, posY + fm.getHeight() - fm.getDescent() /*- fm.getAscent()*/ );
		g2.setFont( oriFont );
	}
	
	@Override
	public int[] medirTexto( String texto, int tama, int sombraX, int sombraY, String familia, 
							 ESTILO estilo ) 
	{
		final int awtEstilo;
		switch (estilo)
		{
			case PLANO: awtEstilo = Font.PLAIN; break;
			case NEGRITA: awtEstilo = Font.BOLD; break;
			case CURSIVA: awtEstilo = Font.ITALIC; break;
			case NEG_CUR: awtEstilo = Font.ITALIC|Font.BOLD; break;
	
			default: awtEstilo = Font.PLAIN;
		}
		
		final FontMetrics fm = g2.getFontMetrics( new Font( familia, awtEstilo, tama ) );
		
		return new int[] { fm.stringWidth( texto ) + sombraX, fm.getHeight() + sombraY };
	}	
	
	public static class KeyStrokeEvent implements AbstractKeyStroke 
	{
		private static final long serialVersionUID = 1L;
		
		final protected KeyEvent kEvent;
		
		public KeyStrokeEvent( KeyEvent kEvent ) 
		{
			this.kEvent = kEvent;
		}
		
		@Override
		public boolean isKeyReleased() { return kEvent.getID() == KeyEvent.KEY_RELEASED; }
		@Override
		public int getModifiers() { return kEvent.getModifiersEx(); }
		@Override
		public String getModifiersText() 
		{ 
			return KeyEvent.getModifiersExText( kEvent.getModifiersEx() ); 
		}
		@Override
		public int getKeycode() { return kEvent.getKeyCode(); }
		@Override
		public char getKeychar() { return kEvent.getKeyChar(); }
		@Override
		public long getTimeStamp() { return kEvent.getWhen(); }
		@Override
		public boolean isAccion() { return kEvent.isActionKey(); }
		@Override
		public LOCALIZACION getLocalizacion() 
		{
			switch ( kEvent.getKeyLocation() ) 
			{
				case KeyEvent.KEY_LOCATION_STANDARD: return LOCALIZACION.ESTANDAR;
				case KeyEvent.KEY_LOCATION_LEFT: return LOCALIZACION.IZQUIERDA;
				case KeyEvent.KEY_LOCATION_RIGHT: return LOCALIZACION.DERECHA;
				case KeyEvent.KEY_LOCATION_NUMPAD: return LOCALIZACION.NUMPAD;
				case KeyEvent.KEY_LOCATION_UNKNOWN: return LOCALIZACION.DESCONOCIDA;
				default: return null;
			}
		}
	}
	
	/**
	 * Implementación de evento de click/toque para AWT/Swing 
	 */
	public static class RatonEvent implements AbstractRatonEvent
	{
		private static final long serialVersionUID = 1L;

		final protected MouseEvent mEvent;
		
		public RatonEvent( MouseEvent mEvent ) 
		{
			this.mEvent = mEvent;
		}
		
		public MouseEvent getMouseEvent() { return mEvent; }
		
		@Override
		public RATON_ID getId() 
		{ 
			switch ( mEvent.getID() )
			{
				case MouseEvent.MOUSE_PRESSED: return RATON_ID.PRESSED;
				case MouseEvent.MOUSE_RELEASED: return RATON_ID.RELEASED;
				case MouseEvent.MOUSE_CLICKED: return RATON_ID.CLICKED;
				case MouseEvent.MOUSE_MOVED: return RATON_ID.MOVED;
				case MouseEvent.MOUSE_DRAGGED: return RATON_ID.DRAGGED;
				case MouseEvent.MOUSE_ENTERED: return RATON_ID.ENTERED;
				case MouseEvent.MOUSE_EXITED: return RATON_ID.EXITED;
				case MouseEvent.MOUSE_WHEEL: return RATON_ID.WHEEL;
				default: return null;
			}
		}
		
		@Override
		public BOTON getButton() 
		{
			switch( mEvent.getButton() )
			{
				case MouseEvent.BUTTON1: return BOTON.BOTON1;
				case MouseEvent.BUTTON2: return BOTON.BOTON2;
				case MouseEvent.BUTTON3: return BOTON.BOTON3;
				case MouseEvent.NOBUTTON:
				default: return BOTON.NO_BOTON;
			}
		}
		
		@Override
		public int getClicks() { return mEvent.getClickCount(); }
		
		@Override
		public int[] getPosicion() { return new int[] { mEvent.getX(), mEvent.getY() }; }
		
		@Override
		public int[] getPosicionEnPantalla()
		{ 
			return new int[] { mEvent.getXOnScreen(), mEvent.getYOnScreen() }; 
		}
		
		@Override
		public long getTimeStamp() { return mEvent.getWhen(); }
		
		@Override
		public double getRotacion() 
		{
			return mEvent instanceof MouseWheelEvent 
				   ? ((MouseWheelEvent) mEvent).getPreciseWheelRotation() 
				   : 0.; 
		}
		
	}  // class RatonEvent
	
}  // class JMEPrimitivas2DParaSwing
