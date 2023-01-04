import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ISolicitud } from '@/shared/model/solicitud.model';

import buildQueryOpts from '@/components/solicitud-filter/filter-utils';

const baseApiUrl = 'api/solicitudes';
const baseFormApiUrl = 'http://localhost:3001/form';

export default class SolicitudService {
  public find(id: string): Promise<ISolicitud> {
    return new Promise<ISolicitud>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findBySolucionId(solucionId: string): Promise<ISolicitud> {
    return new Promise<ISolicitud>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/solucion/${solucionId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}${buildQueryOpts(paginationQuery.filter)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<ISolicitud>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<ISolicitud>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveForms(formId: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseFormApiUrl}/${formId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public send(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<ISolicitud>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/enviar/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
