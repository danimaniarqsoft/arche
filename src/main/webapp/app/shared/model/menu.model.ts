export interface IMenu {
  id?: string | null;
  url?: string | null;
  icon?: string | null;
}

export class Menu implements IMenu {
  constructor(public id?: string, public url?: string | null, public icon?: string | null) {}
}
