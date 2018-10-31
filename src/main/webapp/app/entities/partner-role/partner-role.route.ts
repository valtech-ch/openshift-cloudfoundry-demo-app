import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PartnerRole } from 'app/shared/model/partner-role.model';
import { PartnerRoleService } from './partner-role.service';
import { PartnerRoleComponent } from './partner-role.component';
import { PartnerRoleDetailComponent } from './partner-role-detail.component';
import { PartnerRoleUpdateComponent } from './partner-role-update.component';
import { PartnerRoleDeletePopupComponent } from './partner-role-delete-dialog.component';
import { IPartnerRole } from 'app/shared/model/partner-role.model';

@Injectable({ providedIn: 'root' })
export class PartnerRoleResolve implements Resolve<IPartnerRole> {
    constructor(private service: PartnerRoleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partnerRole: HttpResponse<PartnerRole>) => partnerRole.body));
        }
        return of(new PartnerRole());
    }
}

export const partnerRoleRoute: Routes = [
    {
        path: 'partner-role',
        component: PartnerRoleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-role/:id/view',
        component: PartnerRoleDetailComponent,
        resolve: {
            partnerRole: PartnerRoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-role/new',
        component: PartnerRoleUpdateComponent,
        resolve: {
            partnerRole: PartnerRoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-role/:id/edit',
        component: PartnerRoleUpdateComponent,
        resolve: {
            partnerRole: PartnerRoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partnerRolePopupRoute: Routes = [
    {
        path: 'partner-role/:id/delete',
        component: PartnerRoleDeletePopupComponent,
        resolve: {
            partnerRole: PartnerRoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerRole.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
