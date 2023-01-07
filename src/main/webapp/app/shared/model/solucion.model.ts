import { IComponente } from '@/shared/model/componente.model';

import { EstadoSolucion } from '@/shared/model/enumerations/estado-solucion.model';

export interface ICalendario {
  fechaInicio?: Date | null;
  fechaFinSolicitud?: Date | null;
  fechaFinRevision?: Date | null;
}

export class Calendario implements ICalendario {
  constructor(public fechaInicio?: Date | null, public fechaFinSolicitud?: Date | null, public fechaFinRevision?: Date | null) {}
}

export interface IMensaje {
  bienvenida?: string | null;
  terminos?: string | null;
}

export class Mensaje implements IMensaje {
  constructor(public bienvenida?: string | null, public terminos?: string | null) {}
}
export interface ISolucion {
  id?: string;
  titulo?: string | null;
  descripcion?: string | null;
  estado?: EstadoSolucion | null;
  componentes?: IComponente[] | null;
  mensaje?: Mensaje | null;
  tags?: string[] | null;
  calendario?: ICalendario | null;
  cuestionario?: any | null;
}

export class Solucion implements ISolucion {
  constructor(
    public id?: string,
    public titulo?: string | null,
    public descripcion?: string | null,
    public estado?: EstadoSolucion | null,
    public componentes?: IComponente[] | null,
    public mensaje?: Mensaje | null,
    public tags?: string[] | null,
    public calendario?: ICalendario | null,
    public cuestionario?: any | null
  ) {
    this.cuestionario = this.cuestionario ? this.cuestionario : {};
    this.mensaje = this.mensaje ? this.mensaje : new Mensaje();
    this.calendario = this.calendario ? this.calendario : new Calendario();
    this.componentes = this.componentes ? this.componentes : [];
    this.tags = this.tags ? this.tags : [];
  }
}
