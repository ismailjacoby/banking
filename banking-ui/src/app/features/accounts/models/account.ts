export type AccountType = 'CHECKING' | 'SAVINGS' | 'BUSINESS';
export type Currency = 'EUR' | 'USD' | 'GBP';
export type AccountStatus = 'ACTIVE' | 'BLOCKED' | 'CLOSED';

export interface AccountCreationRequest {
  accountType: AccountType;
  currency: Currency;
}

export interface AccountResponse {
  id: number;
  accountNumber: string;
  balance: number;
  status: AccountStatus;
  accountType: AccountType;
  currency: Currency;
  createdAt: string;
}
