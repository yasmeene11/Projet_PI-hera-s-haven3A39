<?php

namespace App\Entity;

use App\Repository\AccountRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: AccountRepository::class)]
class Account
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'accountId')]
    private ?int $accountId = null;

    #[ORM\Column(length: 255)]
    private ?string $Name = null;

    #[ORM\Column(length: 255)]
    private ?string $Surname = null;

    #[ORM\Column(length: 255)]
    private ?string $Gender = null;

    #[ORM\Column]
    private ?int $Phone_Number = null;

    #[ORM\Column(length: 255)]
    private ?string $Address = null;

    #[ORM\Column(length: 255)]
    private ?string $Email = null;

    #[ORM\Column(length: 255)]
    private ?string $Password = null;

    #[ORM\Column(length: 255)]
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

  


    public function __construct()
    {
        $this->boardings = new ArrayCollection();
        $this->appointments = new ArrayCollection();
        $this->adoptions = new ArrayCollection();
        $this->donationMs = new ArrayCollection();
        $this->donationPs = new ArrayCollection();
    }

    public function getaccountId(): ?int
    {
        return $this->accountId;
    }

    public function getName(): ?string
    {
        return $this->Name;
    }

    public function setName(string $Name): static
    {
        $this->Name = $Name;

        return $this;
    }

    public function getSurname(): ?string
    {
        return $this->Surname;
    }

    public function setSurname(string $Surname): static
    {
        $this->Surname = $Surname;

        return $this;
    }

    public function getGender(): ?string
    {
        return $this->Gender;
    }

    public function setGender(string $Gender): static
    {
        $this->Gender = $Gender;

        return $this;
    }

    public function getPhoneNumber(): ?string
    {
        return $this->Phone_Number;
    }

    public function setPhoneNumber(string $Phone_Number): static
    {
        $this->Phone_Number = $Phone_Number;

        return $this;
    }

    public function getAddress(): ?string
    {
        return $this->Address;
    }

    public function setAddress(string $Address): static
    {
        $this->Address = $Address;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->Email;
    }

    public function setEmail(string $Email): static
    {
        $this->Email = $Email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->Password;
    }

    public function setPassword(string $Password): static
    {
        $this->Password = $Password;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->Role;
    }

    public function setRole(string $Role): static
    {
        $this->Role = $Role;

        return $this;
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
    public function __toString()
    {
        return $this->getName(); // Supposons que 'name' est un champ approprié dans votre entité Account
    }
}
