import { Category } from 'app/shared/model/enumerations/category.model';

export interface IGroceryItem {
  id?: number;
  item?: string | null;
  category?: Category | null;
}

export const defaultValue: Readonly<IGroceryItem> = {};
