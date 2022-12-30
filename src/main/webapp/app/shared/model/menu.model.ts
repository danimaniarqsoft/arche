import { IComponente } from '@/shared/model/componente.model';

export interface IMenu {
  isSendActivated?: boolean | false;
  isPreviewActivated?: boolean | false;
  componentes?: IComponente[] | null;
}

export class Menu implements IMenu {
  constructor(
    public isSendActivated?: boolean | false,
    public isPreviewActivated?: boolean | false,
    public componentes?: IComponente[] | null
  ) {
    this.componentes = this.componentes ? this.componentes : [];
  }
}
