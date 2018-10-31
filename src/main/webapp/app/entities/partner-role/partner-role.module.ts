import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PinguinAppSharedModule } from 'app/shared';
import {
    PartnerRoleComponent,
    PartnerRoleDetailComponent,
    PartnerRoleUpdateComponent,
    PartnerRoleDeletePopupComponent,
    PartnerRoleDeleteDialogComponent,
    partnerRoleRoute,
    partnerRolePopupRoute
} from './';

const ENTITY_STATES = [...partnerRoleRoute, ...partnerRolePopupRoute];

@NgModule({
    imports: [PinguinAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartnerRoleComponent,
        PartnerRoleDetailComponent,
        PartnerRoleUpdateComponent,
        PartnerRoleDeleteDialogComponent,
        PartnerRoleDeletePopupComponent
    ],
    entryComponents: [PartnerRoleComponent, PartnerRoleUpdateComponent, PartnerRoleDeleteDialogComponent, PartnerRoleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PinguinAppPartnerRoleModule {}
