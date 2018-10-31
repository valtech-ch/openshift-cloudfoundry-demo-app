/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerAccountComponent } from 'app/entities/partner-account/partner-account.component';
import { PartnerAccountService } from 'app/entities/partner-account/partner-account.service';
import { PartnerAccount } from 'app/shared/model/partner-account.model';

describe('Component Tests', () => {
    describe('PartnerAccount Management Component', () => {
        let comp: PartnerAccountComponent;
        let fixture: ComponentFixture<PartnerAccountComponent>;
        let service: PartnerAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerAccountComponent],
                providers: []
            })
                .overrideTemplate(PartnerAccountComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerAccountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerAccountService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PartnerAccount('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.partnerAccounts[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
