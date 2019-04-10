package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Visualize extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		int mapSize = (int) (30 + Math.random() * 11);
		
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);
		Game game = new Game(null, null, mapSize, Orientation.Horizontal);
		GridPane gridPane = new GridPane();
		Label lblRedOrbs = new Label("Red Orbs: 0");
		Label lblBlueOrbs = new Label("Blue Orbs: 0");
		Tile[][] tiles = new Tile[mapSize][mapSize];
		
		lblRedOrbs.setTextFill(Color.RED);
		lblBlueOrbs.setTextFill(Color.BLUE);
		
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
		
		Runnable approximate = () ->
		{
			int red = 0;
			int blue = 0;
			for(int r = 0; r < game.getMapSize(); r++)
			{
				for(int c = 0; c < game.getMapSize(); c++)
				{
					Tile tile = tiles[r][c];
					if(tile.getRectangle().getFill().equals(Color.RED.deriveColor(0, 1, 1, 0.25)))
					{
						red++;
					}
					else if(tile.getRectangle().getFill().equals(Color.BLUE.deriveColor(0, 1, 1, 0.25)))
					{
						blue++;
					}
				}
			}
			lblRedOrbs.setText("Red has " + red + " cells");
			lblBlueOrbs.setText("Blue has " + blue + " cells");
		};
		
		Runnable updateGUI = () ->
		{
//			lblRedOrbs.setText("Red Orbs: " + game.getRedPlanet().getOrbCount());
//			lblBlueOrbs.setText("Blue Orbs: " + game.getBluePlanet().getOrbCount());
			
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
			approximate.run();
		};
		
		borderPane.setLeft(gridPane);
		VBox vbox = new VBox(10, lblRedOrbs, lblBlueOrbs);
		borderPane.setRight(vbox);
		
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
				
				final int row = r;
				final int col = c;
				
				tile.getRectangle().setOnMouseClicked(e ->
				{
					Unit unit = game.getUnitAt(row, col);
					UnitTeam unitTeam = (e.getButton() == MouseButton.PRIMARY)? UnitTeam.Red : UnitTeam.Blue;
					
					if(unit == null)
					{
						Voyager voyager = new Voyager(game, unitTeam, row, col);
						game.addUnit(voyager);
						game.playRound();
						updateGUI.run();
					}
				});
			}
		}
		
		stage.setScene(scene);
		stage.setTitle("BattleHack Visualize Thingy");
		stage.show();
	}
}