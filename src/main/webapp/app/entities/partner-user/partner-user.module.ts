import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PinguinAppSharedModule } from 'app/shared';
import {
    PartnerUserComponent,
    PartnerUserDetailComponent,
    PartnerUserUpdateComponent,
    PartnerUserDeletePopupComponent,
    PartnerUserDeleteDialogComponent,
    partnerUserRoute,
    partnerUserPopupRoute
} from './';

const ENTITY_STATES = [...partnerUserRoute, ...partnerUserPopupRoute];

@NgModule({
    imports: [PinguinAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartnerUserComponent,
        PartnerUserDetailComponent,
        PartnerUserUpdateComponent,
        PartnerUserDeleteDialogComponent,
        PartnerUserDeletePopupComponent
    ],
    entryComponents: [PartnerUserComponent, PartnerUserUpdateComponent, PartnerUserDeleteDialogComponent, PartnerUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PinguinAppPartnerUserModule {}
