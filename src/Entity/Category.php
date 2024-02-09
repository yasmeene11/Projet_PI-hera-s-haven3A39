<?php

namespace App\Entity;

use App\Repository\CategoryRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CategoryRepository::class)]
class Category
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $Product_Type = null;

    #[ORM\Column(length: 255)]
    private ?string $Product_Source = null;

    #[ORM\OneToMany(mappedBy: 'category', targetEntity: Product::class)]
    private Collection $Product_Key;

    public function __construct()
    {
        $this->Product_Key = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getProductType(): ?string
    {
        return $this->Product_Type;
    }

    public function setProductType(string $Product_Type): static
    {
        $this->Product_Type = $Product_Type;

        return $this;
    }

    public function getProductSource(): ?string
    {
        return $this->Product_Source;
    }

    public function setProductSource(string $Product_Source): static
    {
        $this->Product_Source = $Product_Source;

        return $this;
    }

    /**
     * @return Collection<int, Product>
     */
    public function getProductKey(): Collection
    {
        return $this->Product_Key;
    }

    public function addProductKey(Product $productKey): static
    {
        if (!$this->Product_Key->contains($productKey)) {
            $this->Product_Key->add($productKey);
            $productKey->setCategory($this);
        }

        return $this;
    }

    public function removeProductKey(Product $productKey): static
    {
        if ($this->Product_Key->removeElement($productKey)) {
            // set the owning side to null (unless already changed)
            if ($productKey->getCategory() === $this) {
                $productKey->setCategory(null);
            }
        }

        return $this;
    }
}
