/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerUserComponent } from 'app/entities/partner-user/partner-user.component';
import { PartnerUserService } from 'app/entities/partner-user/partner-user.service';
import { PartnerUser } from 'app/shared/model/partner-user.model';

describe('Component Tests', () => {
    describe('PartnerUser Management Component', () => {
        let comp: PartnerUserComponent;
        let fixture: ComponentFixture<PartnerUserComponent>;
        let service: PartnerUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerUserComponent],
                providers: []
            })
                .overrideTemplate(PartnerUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PartnerUser('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.partnerUsers[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
