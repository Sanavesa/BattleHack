package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import testing.ConvolvingRobot;
import testing.DannysTestRob;
import testing.RandomRobot;

public class GraphicalProgram extends Application
{
	AbstractRobot redBot;
	AbstractRobot blueBot;
	Random random = new Random();
	int size = 30 + random.nextInt(11); // 30-40

	Game game = new Game(new RandomRobot(), new ConvolvingRobot(), size, Orientation.Horizontal);
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{

		
		// TODO: CHANGE ME HERE DANIEL! Change RandomRobot below to your own implementation to test!
		
		Tile[][] tiles = new Tile[game.getMapSize()][game.getMapSize()];
		GridPane gridPane = new GridPane();
		for(int r = 0; r < game.getMapSize(); r++)
		{
			for(int c = 0; c < game.getMapSize(); c++)
			{
				Tile tile = new Tile();
				tiles[r][c] = tile;
				gridPane.add(tile, c, r);
				
				tile.getLabel().setText(String.valueOf(game.getOrbValueAt(r, c)));
				if(game.getOrbValueAt(r, c) == 0)
				{
					tile.getRectangle().setFill(Color.BLACK);
				}
			}
		}
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(gridPane);
		Scene scene = new Scene(borderPane);
		
		Label lblRound = new Label("Round 1 / 256");
		Label lblRedOrbs = new Label("Red Orbs: 0");
		Label lblBlueOrbs = new Label("Blue Orbs: 0");
		
		Label lblRedBots = new Label("RED Team");
		lblRedBots.setStyle("-fx-font-size: 16");
		
		
		Button btnRConvolvingBot = new Button("Convolving");
		btnRConvolvingBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 

		
		Button btnRDannysTestBot = new Button("SkyNet");
		btnRDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 

		
		Button btnRRandomBot = new Button("Random");
		btnRRandomBot.setStyle("-fx-background-color: 'darkRed'; -fx-text-fill: 'white'");
		redBot = new RandomRobot();
		
		
		

		btnRRandomBot.setOnAction(e ->
		{
			btnRRandomBot.setStyle("-fx-background-color: 'darkRed'; -fx-text-fill: 'white'"); 
			btnRConvolvingBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnRDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			game.setRedRobot(new RandomRobot());

		});
		
		btnRConvolvingBot.setOnAction(e ->
		{
			btnRRandomBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnRConvolvingBot.setStyle("-fx-background-color: 'darkRed'; -fx-text-fill: 'white'"); 
			btnRDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			game.setRedRobot(new ConvolvingRobot());

		});
		
		btnRDannysTestBot.setOnAction(e ->
		{
			btnRRandomBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnRConvolvingBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnRDannysTestBot.setStyle("-fx-background-color: 'darkRed'; -fx-text-fill: 'white'"); 
			game.setRedRobot(new DannysTestRob());


		});
		

		Label lblBlueBots = new Label("BLUE Team");
		lblBlueBots.setStyle("-fx-font-size: 16");
		

		Button btnBConvolvingBot = new Button("Convolving");
		btnBConvolvingBot.setStyle("-fx-background-color: 'darkblue'; -fx-text-fill: 'white'"); 
		blueBot = new ConvolvingRobot();
		

		Button btnBDannysTestBot = new Button("SkyNet");
		btnBDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 

		Button btnBRandomBot = new Button("Random");
		btnBRandomBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 

		
		btnBConvolvingBot.setOnAction(e ->
		{
			btnBRandomBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnBConvolvingBot.setStyle("-fx-background-color: 'darkblue'; -fx-text-fill: 'white'"); 
			btnBDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
		
			game.setBlueRobot(new ConvolvingRobot());

		});
		
		btnBDannysTestBot.setOnAction(e ->
		{
			btnBRandomBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnBConvolvingBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnBDannysTestBot.setStyle("-fx-background-color: 'darkblue'; -fx-text-fill: 'white'"); 
		
			game.setBlueRobot(new DannysTestRob());

		});
		
		btnBRandomBot.setOnAction(e ->
		{
			btnBRandomBot.setStyle("-fx-background-color: 'darkblue'; -fx-text-fill: 'white'"); 
			btnBConvolvingBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
			btnBDannysTestBot.setStyle("-fx-background-color: 'lightgrey'; -fx-text-fill: 'black'"); 
	
			game.setBlueRobot(new RandomRobot());

		});
		
		Runnable onRestart = () ->
		{
			gridPane.getChildren().clear();
			for(int r = 0; r < game.getMapSize(); r++)
			{
				for(int c = 0; c < game.getMapSize(); c++)
				{
					Tile tile = new Tile();
					tiles[r][c] = tile;
					gridPane.add(tile, c, r);
					
					tile.getLabel().setText(String.valueOf(game.getOrbValueAt(r, c)));
					if(game.getOrbValueAt(r, c) == 0)
					{
						tile.getRectangle().setFill(Color.BLACK);
					}
				}
			}
		};
		
		Runnable tintOwnership = () ->
		{
			List<Unit> closestUnits = new ArrayList<>();
			int closestDistance = Integer.MAX_VALUE;
			
			for(int r = 0; r < game.getMapSize(); r++)
			{
				for(int c = 0; c < game.getMapSize(); c++)
				{
					int orbs = game.getOrbValueAt(r, c);
					
					if(orbs == 0)
						continue;
					
					closestDistance = Integer.MAX_VALUE;
					closestUnits.clear();
					
					// Find closest distance unit
					for(Unit unit : game.getUnits())
					{
						if(unit.getUnitType() == UnitType.Planet)
							continue;
						
						int distance = Game.manhattanDistance(r, c, unit.getRow(), unit.getCol());
						if(distance < closestDistance)
						{
							closestDistance = distance;
							closestUnits.clear();
							closestUnits.add(unit);
						}
						else if(distance == closestDistance)
						{
							closestUnits.add(unit);
						}
					}
					
					if(closestUnits.size() == 0)
						continue;
					
					UnitTeam closestTeam = closestUnits.get(0).getUnitTeam();
					boolean allSameTeam = true;
					for(int i = 1; i < closestUnits.size(); i++)
					{
						if(closestTeam != closestUnits.get(i).getUnitTeam())
						{
							allSameTeam = false;
							break;
						}
					}
					
					Tile tile = tiles[r][c];
					if(allSameTeam)
					{
						if(closestTeam == UnitTeam.Red)
						{
							tile.getRectangle().setFill(Color.RED.deriveColor(0, 1, 1, 0.25));
						}
						else
						{
							tile.getRectangle().setFill(Color.BLUE.deriveColor(0, 1, 1, 0.25));
						}
					}
					else
					{
						tile.getRectangle().setFill(Color.WHITESMOKE);
					}
				}
			}
		};
		
		Runnable updateGUI = () ->
		{
			lblRound.setText("Round " + game.getRoundCounter() + " / " + Game.getMaxRounds());
			lblRedOrbs.setText("Red Orbs: " + game.getRedPlanet().getOrbCount() + ".\n Total collected = " + game.getRedPlanet().getTotalOrbsCollected());
			lblBlueOrbs.setText("Blue Orbs: " + game.getBluePlanet().getOrbCount() + ".\n Total collected = " + game.getBluePlanet().getTotalOrbsCollected());
			
			if(game.getRedPlanet().getTotalOrbsCollected() > game.getBluePlanet().getTotalOrbsCollected())
			{
				lblRedOrbs.setTextFill(Color.GREEN);
				lblBlueOrbs.setTextFill(Color.RED);
			}
			
			else if(game.getRedPlanet().getTotalOrbsCollected() < game.getBluePlanet().getTotalOrbsCollected())
			{
				lblRedOrbs.setTextFill(Color.RED);
				lblBlueOrbs.setTextFill(Color.GREEN);
			}
			
			else
			{
				lblRedOrbs.setTextFill(Color.BLACK);
				lblBlueOrbs.setTextFill(Color.BLACK);
			}
			
			
			for(int r = 0; r < game.getMapSize(); r++)
			{
				for(int c = 0; c < game.getMapSize(); c++)
				{
					Tile tile = tiles[r][c];
					Unit unit = game.getUnitAt(r, c);
					
					if(unit == null)
					{
						tile.getCircle().setRadius(0);
					}
					else
					{
						if(unit.getUnitType() == UnitType.Planet)
						{
							tile.getCircle().setRadius(6);
						}
						else
						{
							tile.getCircle().setRadius(4);
						}
						
						if(unit.getUnitTeam() == UnitTeam.Red)
						{
							tile.getCircle().setFill(Color.RED);
						}
						else
						{
							tile.getCircle().setFill(Color.BLUE);
						}
					}
				}
			}
			
			tintOwnership.run();
		};
		
		Button btnNextRound = new Button("Play Next Round");
		btnNextRound.setOnAction(e ->
		{
			if(game.getRoundCounter() < Game.MAX_ROUNDS)
			{
				game.playRound();
				updateGUI.run();
			}
		});
		
		Button btnRestart = new Button("Restart");
		btnRestart.setOnAction(e ->
		{
			game.restart();
			onRestart.run();
			updateGUI.run();
		});
		
		Button btnPlayMatch = new Button("Play Match");
		btnPlayMatch.setOnAction(e ->
		{

			Timeline timeline = new Timeline();
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2), e2 ->
			{
				if(game.getRoundCounter() >= Game.MAX_ROUNDS)
				{
					timeline.stop();
					btnPlayMatch.setDisable(false);
					btnNextRound.setDisable(false);
					btnRestart.setDisable(false);
					
					btnRRandomBot.setDisable(false);
					btnRConvolvingBot.setDisable(false);
					btnRDannysTestBot.setDisable(false);

					btnBRandomBot.setDisable(false);
					btnBConvolvingBot.setDisable(false);
					btnBDannysTestBot.setDisable(false);
				}
				else
				{
					game.playRound();
					updateGUI.run();
					btnPlayMatch.setDisable(true);
					btnNextRound.setDisable(true);
					btnRestart.setDisable(true);
					
					btnRRandomBot.setDisable(true);
					btnRConvolvingBot.setDisable(true);
					btnRDannysTestBot.setDisable(true);

					btnBRandomBot.setDisable(true);
					btnBConvolvingBot.setDisable(true);
					btnBDannysTestBot.setDisable(true);
				}
			}));
			timeline.play();
		});
		
		Button btnHeatmap = new Button("Show Heatmap");
		btnHeatmap.setOnAction(e ->
		{
			Stage heatmapStage = new Stage();
			
			GridPane heatmapTilesGridPane = new GridPane();
			for(int r = 0; r < game.getMapSize(); r++)
			{
				for(int c = 0; c < game.getMapSize(); c++)
				{
					Tile tile = new Tile();
					heatmapTilesGridPane.add(tile, c, r);
					
					tile.getLabel().setText(String.valueOf(game.getOrbValueAt(r, c)));
					double hotness = game.getOrbValueAt(r, c) / 7.0;
					double hue = 70 * (1 - hotness);
					tile.getRectangle().setFill(Color.hsb(hue, 1, 1));
				}
			}
			
			BorderPane heatmapTilesBorderPane = new BorderPane();
			heatmapTilesBorderPane.setCenter(heatmapTilesGridPane);
			Scene heatmapTilesScene = new Scene(heatmapTilesBorderPane);
			
			heatmapStage.setScene(heatmapTilesScene);
			heatmapStage.setTitle("Heatmap");
			heatmapStage.show();
		});
		
		updateGUI.run();
		
		VBox vbox = new VBox(lblRound, btnNextRound, btnPlayMatch, btnRestart, lblRedOrbs, lblBlueOrbs, btnHeatmap, new Label(" \n\n"),lblRedBots,btnRConvolvingBot,btnRDannysTestBot,btnRRandomBot,lblBlueBots,btnBConvolvingBot,btnBDannysTestBot,btnBRandomBot);
		
		borderPane.setRight(vbox);
		
		stage.setScene(scene);
		stage.setTitle("BattleHack");
		stage.show();
	}
}

class Tile extends Pane
{
	public Tile()
	{
		rectangle = new Rectangle(24, 24, Color.WHITESMOKE);
		rectangle.setStyle("-fx-stroke: black;" + 
				 "-fx-stroke-width: 1;" + 
				 "-fx-padding: 1;" + 
				 "-fx-stroke-type: inside;"); 
		label = new Label("");
		
		circle = new Circle(12, 12, 0);
		
		getChildren().addAll(rectangle, label, circle);
		label.setPadding(new Insets(1));
	}
	
	public Rectangle getRectangle()
	{
		return rectangle;
	}
	
	public Label getLabel()
	{
		return label;
	}
	
	public Circle getCircle()
	{
		return circle;
	}

	private final Rectangle rectangle;
	private final Label label;
	private final Circle circle;
}