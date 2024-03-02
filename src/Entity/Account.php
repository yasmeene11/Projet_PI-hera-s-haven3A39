<?php

namespace App\Entity;

use App\Repository\AccountRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: AccountRepository::class)]
class Account implements UserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'accountId')]
    private ?int $accountId = null;

    #[ORM\Column(length: 255,nullable: false)]
    #[Assert\NotNull(message: "Name cannot be empty")]
    private ?string $Name = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Surname cannot be empty")]
    private ?string $Surname = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Gender cannot be empty")]
    private ?string $Gender = null;

    #[ORM\Column(nullable: false)]
    #[Assert\Regex(pattern: "/^\d{8}$/", message: "Phone number must be 8 digits")]
    #[Assert\NotNull(message: "Phone number cannot be empty")]
    private ?string $Phone_Number = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Address cannot be empty")]
    private ?string $Address = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Email cannot be empty")]
    #[Assert\Email(message: "Invalid email format")]
    private ?string $Email = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Password cannot be empty")]
    #[Assert\Length(
        min: 6,
        minMessage: "Password must be at least {{ limit }} characters long"
    )]
    #[Assert\Regex(
        pattern: '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{6,}$/',
        message: 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one symbol, and must be at least 6 characters long'
    )]
    private ?string $Password = null;

    #[ORM\Column(length: 255, nullable: false)]
    #[Assert\NotNull(message: "Role cannot be empty")]
    private ?string $Role = null;

    #[ORM\OneToMany(mappedBy: 'Account_Key', targetEntity: Boarding::class)]
    private Collection $boardings;

    #[ORM\OneToMany(mappedBy: 'Account_Key', targetEntity: Appointment::class)]
    private Collection $appointments;

    #[ORM\OneToMany(mappedBy: 'Account_Key', targetEntity: Adoption::class)]
    private Collection $adoptions;

    #[ORM\OneToMany(mappedBy: 'Account_Key', targetEntity: DonationM::class)]
    private Collection $donationMs;

    #[ORM\OneToMany(mappedBy: 'Account_Key', targetEntity: DonationP::class)]
    private Collection $donationPs;

    #[ORM\Column(length: 255, nullable: false)]
    private ?string $Account_Status = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $resetToken = null;

    #[ORM\Column(nullable: true)]
    private ?\DateTimeImmutable $resetTokenRequestedAt = null; 

    public function __construct()
    {
        $this->boardings = new ArrayCollection();
        $this->appointments = new ArrayCollection();
        $this->adoptions = new ArrayCollection();
        $this->donationMs = new ArrayCollection();
        $this->donationPs = new ArrayCollection();
    }

    
    public function __toString():string
    {
        return $this->Name;
    }
    public function getaccountId(): ?int
    {
        return $this->accountId;
    }

    public function getName(): ?string
    {
        return $this->Name;
    }

    public function setName(?string $Name): static
    {
        $this->Name = $Name;

        return $this;
    }

    public function getSurname(): ?string
    {
        return $this->Surname;
    }

    public function setSurname(?string $Surname): static
    {
        $this->Surname = $Surname;

        return $this;
    }

    public function getGender(): ?string
    {
        return $this->Gender;
    }

    public function setGender(?string $Gender): static
    {
        $this->Gender = $Gender;

        return $this;
    }

    public function getPhoneNumber(): ?string
    {
        return $this->Phone_Number;
    }

    public function setPhoneNumber(?string $Phone_Number): static
    {
        $this->Phone_Number = $Phone_Number;

        return $this;
    }

    public function getAddress(): ?string
    {
        return $this->Address;
    }

    public function setAddress(?string $Address): static
    {
        $this->Address = $Address;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->Email;
    }

    public function setEmail(?string $Email): static
    {
        $this->Email = $Email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->Password;
    }

    public function setPassword(?string $Password): static
    {
        $this->Password = $Password;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->Role;
    }

    public function setRole(?string $Role): static
    {
        $this->Role = $Role;

        return $this;
    }
    public function getRoles(): array
    {
        // Return the roles for the user
        return [$this->Role]; // Assuming Role is the role of the user
    }

    public function getUsername(): string
    {
        // Return the username for the user
        return $this->Email; // Assuming Email is the username
    }

    public function getSalt(): ?string
    {
        // You can return null if not using a salt
        return null;
    }

    public function eraseCredentials()
    {
        // Implement if you need to erase sensitive data from the user
        // For example, clear plaintext password
        $this->Password = null;
    }

    /**
     * @return Collection<int, Boarding>
     */
    public function getBoardings(): Collection
    {
        return $this->boardings;
    }

    public function addBoarding(Boarding $boarding): static
    {
        if (!$this->boardings->contains($boarding)) {
            $this->boardings->add($boarding);
            $boarding->setAccountKey($this);
        }

        return $this;
    }

    public function removeBoarding(Boarding $boarding): static
    {
        if ($this->boardings->removeElement($boarding)) {
            // set the owning side to null (unless already changed)
            if ($boarding->getAccountKey() === $this) {
                $boarding->setAccountKey(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Appointment>
     */
    public function getAppointments(): Collection
    {
        return $this->appointments;
    }

    public function addAppointment(Appointment $appointment): static
    {
        if (!$this->appointments->contains($appointment)) {
            $this->appointments->add($appointment);
            $appointment->setAccountKey($this);
        }

        return $this;
    }

    public function removeAppointment(Appointment $appointment): static
    {
        if ($this->appointments->removeElement($appointment)) {
            // set the owning side to null (unless already changed)
            if ($appointment->getAccountKey() === $this) {
                $appointment->setAccountKey(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Adoption>
     */
    public function getAdoptions(): Collection
    {
        return $this->adoptions;
    }

    public function addAdoption(Adoption $adoption): static
    {
        if (!$this->adoptions->contains($adoption)) {
            $this->adoptions->add($adoption);
            $adoption->setAccountKey($this);
        }

        return $this;
    }

    public function removeAdoption(Adoption $adoption): static
    {
        if ($this->adoptions->removeElement($adoption)) {
            // set the owning side to null (unless already changed)
            if ($adoption->getAccountKey() === $this) {
                $adoption->setAccountKey(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, DonationM>
     */
    public function getDonationMs(): Collection
    {
        return $this->donationMs;
    }

    public function addDonationM(DonationM $donationM): static
    {
        if (!$this->donationMs->contains($donationM)) {
            $this->donationMs->add($donationM);
            $donationM->setAccountKey($this);
        }

        return $this;
    }

    public function removeDonationM(DonationM $donationM): static
    {
        if ($this->donationMs->removeElement($donationM)) {
            // set the owning side to null (unless already changed)
            if ($donationM->getAccountKey() === $this) {
                $donationM->setAccountKey(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, DonationP>
     */
    public function getDonationPs(): Collection
    {
        return $this->donationPs;
    }

    public function addDonationP(DonationP $donationP): static
    {
        if (!$this->donationPs->contains($donationP)) {
            $this->donationPs->add($donationP);
            $donationP->setAccountKey($this);
        }

        return $this;
    }

    public function removeDonationP(DonationP $donationP): static
    {
        if ($this->donationPs->removeElement($donationP)) {
            // set the owning side to null (unless already changed)
            if ($donationP->getAccountKey() === $this) {
                $donationP->setAccountKey(null);
            }
        }

        return $this;
    }

    public function getAccountStatus(): ?string
    {
        return $this->Account_Status;
    }

    public function setAccountStatus(string $Account_Status): static
    {
        $this->Account_Status = $Account_Status;

        return $this;
    }

    public function getResetToken(): ?string
    {
        return $this->resetToken;
    }

    public function setResetToken(?string $resetToken): static
    {
        $this->resetToken = $resetToken;

        return $this;
    }

    public function getResetTokenRequestedAt(): ?\DateTimeImmutable
    {
        return $this->resetTokenRequestedAt;
    }

    public function setResetTokenRequestedAt(?\DateTimeImmutable $resetTokenRequestedAt): static
    {
        $this->resetTokenRequestedAt = $resetTokenRequestedAt;
        return $this;
    }
}