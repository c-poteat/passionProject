export interface IFavoriteRecipes {
  id?: number;
  favoritelinks?: string | null;
}

export const defaultValue: Readonly<IFavoriteRecipes> = {};
