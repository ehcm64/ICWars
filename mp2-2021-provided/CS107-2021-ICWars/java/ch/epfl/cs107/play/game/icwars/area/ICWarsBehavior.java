package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {
	public enum ICWarsCellType {
		// https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
		NONE(0, 0), // Should never be used except
					// in the toType method
		ROAD(-16777216, 0), // the second value is the number
							// of defense stars
		PLAIN(-14112955, 1),
		WOOD(-65536, 3),
		RIVER(-16776961, 0),
		MOUNTAIN(-256, 4),
		CITY(-1, 2);

		final int type;
		final int defStars;

		ICWarsCellType(int type, int defStars) {
			this.type = type;
			this.defStars = defStars;
		}

		/** Get the type of a cell by knowing the integer value of its type.
		 * 
		 * @param type (int): the integer value.
		 * @return (ICWarsCellType): the type of the cell from the enum.
		 */
		public static ICWarsCellType toType(int type) {
			for (ICWarsCellType ict : ICWarsCellType.values()) {
				if (ict.type == type)
					return ict;
			}
			System.out.println(type);
			return NONE;
		}

		/** Get the number of defense stars of a cell.
		 * 
		 * @return (int): the number of defense stars.
		 */
		public int getDefenseStar() {
			return this.defStars;
		}

		/** Transforms the type of a cell to a String.
		 * 
		 */
        public String typeToString() {
            return this.toString();
        }
	}

	/**
	 * Default Tuto2Behavior Constructor
	 * 
	 * @param window (Window), not null
	 * @param name   (String): Name of the Behavior, not null
	 */
	public ICWarsBehavior(Window window, String name) {
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ICWarsCellType color = ICWarsCellType.toType(getRGB(height - 1 - y, x));
				setCell(x, y, new ICWarsCell(x, y, color));
			}
		}
	}

	/** Get the type of the cell by knowing its x and y coordinates.
	 * 
	 * @param x (int): the x position of the cell.
	 * @param y (int): the y position of the cell.
	 * @return (ICWarsCellType): the type of the cell.
	 */
	public ICWarsCellType getCellType(int x, int y) {
		ICWarsCell cell = (ICWarsCell)getCell(x, y);
		ICWarsCellType type = cell.type;
		return type;
	}

	public class ICWarsCell extends AreaBehavior.Cell {
		private final ICWarsCellType type;

		/**
		 * DefaultICWarsCell Constructor
		 * 
		 * @param x    (int): x coordinate of the cell
		 * @param y    (int): y coordinate of the cell
		 * @param type (ICWarsCellType), not null
		 */
		public ICWarsCell(int x, int y, ICWarsCellType type) {
			super(x, y);
			this.type = type;
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			boolean cellTraversable = true;
			for (Interactable ent : this.entities) {
				if (ent.takeCellSpace())
					cellTraversable = false;
			}
			if (entity.takeCellSpace() && !cellTraversable) {
				return false;
			}
			return true;
		}

		@Override
		public boolean isCellInteractable() {
			return true;
		}

		@Override
		public boolean isViewInteractable() {
			return false;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
		}
	}
}