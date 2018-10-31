import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerRole } from 'app/shared/model/partner-role.model';

@Component({
    selector: 'jhi-partner-role-detail',
    templateUrl: './partner-role-detail.component.html'
})
export class PartnerRoleDetailComponent implements OnInit {
    partnerRole: IPartnerRole;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerRole }) => {
            this.partnerRole = partnerRole;
        });
    }

    previousState() {
        window.history.back();
    }
}
