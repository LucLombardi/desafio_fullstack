export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; // Página atual (zero-based)
  first: boolean;
  last: boolean;
  empty: boolean;
}
