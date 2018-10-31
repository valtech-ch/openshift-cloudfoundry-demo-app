import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartner } from 'app/shared/model/partner.model';
import { Principal } from 'app/core';
import { PartnerService } from './partner.service';

@Component({
    selector: 'jhi-partner',
    templateUrl: './partner.component.html'
})
export class PartnerComponent implements OnInit, OnDestroy {
    partners: IPartner[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private partnerService: PartnerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.partnerService.query().subscribe(
            (res: HttpResponse<IPartner[]>) => {
                this.partners = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPartners();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPartner) {
        return item.id;
    }

    registerChangeInPartners() {
        this.eventSubscriber = this.eventManager.subscribe('partnerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
