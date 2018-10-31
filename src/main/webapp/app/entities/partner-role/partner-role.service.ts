import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartnerRole } from 'app/shared/model/partner-role.model';

type EntityResponseType = HttpResponse<IPartnerRole>;
type EntityArrayResponseType = HttpResponse<IPartnerRole[]>;

@Injectable({ providedIn: 'root' })
export class PartnerRoleService {
    public resourceUrl = SERVER_API_URL + 'api/partner-roles';

    constructor(private http: HttpClient) {}

    create(partnerRole: IPartnerRole): Observable<EntityResponseType> {
        return this.http.post<IPartnerRole>(this.resourceUrl, partnerRole, { observe: 'response' });
    }

    update(partnerRole: IPartnerRole): Observable<EntityResponseType> {
        return this.http.put<IPartnerRole>(this.resourceUrl, partnerRole, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IPartnerRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPartnerRole[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
