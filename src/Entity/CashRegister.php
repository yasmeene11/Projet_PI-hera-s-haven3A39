<?php

namespace App\Entity;

use App\Repository\CashRegisterRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CashRegisterRepository::class)]
class CashRegister
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?float $Balance = null;

    #[ORM\Column]
    private ?float $Expenses = null;

    #[ORM\Column]
    private ?float $Donation_Total = null;

    #[ORM\Column]
    private ?float $Adoption_Fee_Total = null;

    #[ORM\Column]
    private ?float $Boarding_Fee_Total = null;

    #[ORM\OneToMany(mappedBy: 'Boarding_Key', targetEntity: Adoption::class)]
    private Collection $Adoption_Key;

    #[ORM\OneToMany(mappedBy: 'cashRegister', targetEntity: Boarding::class)]
    private Collection $Boarding_Key;

    #[ORM\OneToMany(mappedBy: 'cashRegister', targetEntity: DonationM::class)]
    private Collection $DonationM_Key;

    public function __construct()
    {
        $this->Adoption_Key = new ArrayCollection();
        $this->Boarding_Key = new ArrayCollection();
        $this->DonationM_Key = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getBalance(): ?float
    {
        return $this->Balance;
    }

    public function setBalance(float $Balance): static
    {
        $this->Balance = $Balance;

        return $this;
    }

    public function getExpenses(): ?float
    {
        return $this->Expenses;
    }

    public function setExpenses(float $Expenses): static
    {
        $this->Expenses = $Expenses;

        return $this;
    }

    public function getDonationTotal(): ?float
    {
        return $this->Donation_Total;
    }

    public function setDonationTotal(float $Donation_Total): static
    {
        $this->Donation_Total = $Donation_Total;

        return $this;
    }

    public function getAdoptionFeeTotal(): ?float
    {
        return $this->Adoption_Fee_Total;
    }

    public function setAdoptionFeeTotal(float $Adoption_Fee_Total): static
    {
        $this->Adoption_Fee_Total = $Adoption_Fee_Total;

        return $this;
    }

    public function getBoardingFeeTotal(): ?float
    {
        return $this->Boarding_Fee_Total;
    }

    public function setBoardingFeeTotal(float $Boarding_Fee_Total): static
    {
        $this->Boarding_Fee_Total = $Boarding_Fee_Total;

        return $this;
    }

    /**
     * @return Collection<int, Adoption>
     */
    public function getAdoptionKey(): Collection
    {
        return $this->Adoption_Key;
    }

    public function addAdoptionKey(Adoption $adoptionKey): static
    {
        if (!$this->Adoption_Key->contains($adoptionKey)) {
            $this->Adoption_Key->add($adoptionKey);
            $adoptionKey->setcashRegister($this);
        }

        return $this;
    }

    public function removeAdoptionKey(Adoption $adoptionKey): static
    {
        if ($this->Adoption_Key->removeElement($adoptionKey)) {
            // set the owning side to null (unless already changed)
            if ($adoptionKey->getcashRegister() === $this) {
                $adoptionKey->setcashRegister(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Boarding>
     */
    public function getBoardingKey(): Collection
    {
        return $this->Boarding_Key;
    }

    public function addBoardingKey(Boarding $boardingKey): static
    {
        if (!$this->Boarding_Key->contains($boardingKey)) {
            $this->Boarding_Key->add($boardingKey);
            $boardingKey->setCashRegister($this);
        }

        return $this;
    }

    public function removeBoardingKey(Boarding $boardingKey): static
    {
        if ($this->Boarding_Key->removeElement($boardingKey)) {
            // set the owning side to null (unless already changed)
            if ($boardingKey->getCashRegister() === $this) {
                $boardingKey->setCashRegister(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, DonationM>
     */
    public function getDonationMKey(): Collection
    {
        return $this->DonationM_Key;
    }

    public function addDonationMKey(DonationM $donationMKey): static
    {
        if (!$this->DonationM_Key->contains($donationMKey)) {
            $this->DonationM_Key->add($donationMKey);
            $donationMKey->setCashRegister($this);
        }

        return $this;
    }

    public function removeDonationMKey(DonationM $donationMKey): static
    {
        if ($this->DonationM_Key->removeElement($donationMKey)) {
            // set the owning side to null (unless already changed)
            if ($donationMKey->getCashRegister() === $this) {
                $donationMKey->setCashRegister(null);
            }
        }

        return $this;
    }
}
