import { ISolucion } from '@/shared/model/solucion.model';

export interface IComponente {
  orden?: number | null;
  titulo?: string | null;
  descripcion?: string | null;
  formId?: string | null;
  icon?: string | null;
  tipo?: string | null;
}

export class Componente implements IComponente {
  constructor(
    public orden?: number | null,
    public titulo?: string | null,
    public descripcion?: string | null,
    public formId?: string | null,
    public icon?: string | null,
    public tipo?: string | null
  ) {}
}
