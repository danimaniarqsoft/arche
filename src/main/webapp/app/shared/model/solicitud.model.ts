export interface ISolicitud {
  id?: string;
  nombre?: string | null;
}

export class Solicitud implements ISolicitud {
  constructor(public id?: string, public nombre?: string | null) {}
}
