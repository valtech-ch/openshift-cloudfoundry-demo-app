import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PartnerAccount } from 'app/shared/model/partner-account.model';
import { PartnerAccountService } from './partner-account.service';
import { PartnerAccountComponent } from './partner-account.component';
import { PartnerAccountDetailComponent } from './partner-account-detail.component';
import { PartnerAccountUpdateComponent } from './partner-account-update.component';
import { PartnerAccountDeletePopupComponent } from './partner-account-delete-dialog.component';
import { IPartnerAccount } from 'app/shared/model/partner-account.model';

@Injectable({ providedIn: 'root' })
export class PartnerAccountResolve implements Resolve<IPartnerAccount> {
    constructor(private service: PartnerAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partnerAccount: HttpResponse<PartnerAccount>) => partnerAccount.body));
        }
        return of(new PartnerAccount());
    }
}

export const partnerAccountRoute: Routes = [
    {
        path: 'partner-account',
        component: PartnerAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-account/:id/view',
        component: PartnerAccountDetailComponent,
        resolve: {
            partnerAccount: PartnerAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-account/new',
        component: PartnerAccountUpdateComponent,
        resolve: {
            partnerAccount: PartnerAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-account/:id/edit',
        component: PartnerAccountUpdateComponent,
        resolve: {
            partnerAccount: PartnerAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partnerAccountPopupRoute: Routes = [
    {
        path: 'partner-account/:id/delete',
        component: PartnerAccountDeletePopupComponent,
        resolve: {
            partnerAccount: PartnerAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pinguinApp.partnerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
