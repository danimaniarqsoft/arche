export interface ISolicitud {
  id?: string;
  nombre?: string | null;
  usuario?: string | null;
  solucionId?: string | null;
}

export class Solicitud implements ISolicitud {
  constructor(public id?: string, public nombre?: string | null, public usuario?: string | null, public solucionId?: string | null) {}
}
