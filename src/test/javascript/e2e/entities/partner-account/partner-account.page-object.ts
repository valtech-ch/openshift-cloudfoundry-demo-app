import { element, by, ElementFinder } from 'protractor';

export class PartnerAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-partner-account div table .btn-danger'));
    title = element.all(by.css('jhi-partner-account div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PartnerAccountUpdatePage {
    pageTitle = element(by.id('jhi-partner-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    accountNameInput = element(by.id('field_accountName'));
    partnerSelect = element(by.id('field_partner'));
    partnerUserSelect = element(by.id('field_partnerUser'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAccountNameInput(accountName) {
        await this.accountNameInput.sendKeys(accountName);
    }

    async getAccountNameInput() {
        return this.accountNameInput.getAttribute('value');
    }

    async partnerSelectLastOption() {
        await this.partnerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async partnerSelectOption(option) {
        await this.partnerSelect.sendKeys(option);
    }

    getPartnerSelect(): ElementFinder {
        return this.partnerSelect;
    }

    async getPartnerSelectedOption() {
        return this.partnerSelect.element(by.css('option:checked')).getText();
    }

    async partnerUserSelectLastOption() {
        await this.partnerUserSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async partnerUserSelectOption(option) {
        await this.partnerUserSelect.sendKeys(option);
    }

    getPartnerUserSelect(): ElementFinder {
        return this.partnerUserSelect;
    }

    async getPartnerUserSelectedOption() {
        return this.partnerUserSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PartnerAccountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-partnerAccount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-partnerAccount'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
