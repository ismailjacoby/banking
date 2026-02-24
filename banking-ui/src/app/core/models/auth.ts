export interface AddressRequest {
  street: string;
  zipCode: string;
  city: string;
  country: string;
}

export interface AuthResponse {
  username: string;
  token: string;
  role: 'CUSTOMER' | 'ADMIN';
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  gender: 'MALE' | 'FEMALE' | 'OTHER';
  nationality: string;
  phoneNumber: string;
  email: string;
  password: string;
  address: AddressRequest;
}
