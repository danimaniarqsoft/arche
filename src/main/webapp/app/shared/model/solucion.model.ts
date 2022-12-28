import { IComponente } from '@/shared/model/componente.model';

import { EstadoSolucion } from '@/shared/model/enumerations/estado-solucion.model';
export interface ISolucion {
  id?: string;
  titulo?: string | null;
  descripcion?: string | null;
  estado?: EstadoSolucion | null;
  componentes?: IComponente[] | null;
}

export class Solucion implements ISolucion {
  constructor(
    public id?: string,
    public titulo?: string | null,
    public descripcion?: string | null,
    public estado?: EstadoSolucion | null,
    public componentes?: IComponente[] | null
  ) {
    this.componentes = this.componentes ? this.componentes : [];
  }
}
