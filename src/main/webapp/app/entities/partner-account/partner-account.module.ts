import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PinguinAppSharedModule } from 'app/shared';
import {
    PartnerAccountComponent,
    PartnerAccountDetailComponent,
    PartnerAccountUpdateComponent,
    PartnerAccountDeletePopupComponent,
    PartnerAccountDeleteDialogComponent,
    partnerAccountRoute,
    partnerAccountPopupRoute
} from './';

const ENTITY_STATES = [...partnerAccountRoute, ...partnerAccountPopupRoute];

@NgModule({
    imports: [PinguinAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartnerAccountComponent,
        PartnerAccountDetailComponent,
        PartnerAccountUpdateComponent,
        PartnerAccountDeleteDialogComponent,
        PartnerAccountDeletePopupComponent
    ],
    entryComponents: [
        PartnerAccountComponent,
        PartnerAccountUpdateComponent,
        PartnerAccountDeleteDialogComponent,
        PartnerAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PinguinAppPartnerAccountModule {}
