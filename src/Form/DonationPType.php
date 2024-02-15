<?php

namespace App\Form;

use App\Entity\DonationP;
use App\Entity\Account;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;


use Symfony\Component\OptionsResolver\OptionsResolver;
class DonationPType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Donation_Product_Name', TextType::class, [
                'label' => 'Product Name',
            ])
            ->add('Donation_Product_Quantity', IntegerType::class, [
                'label' => 'Product Quantity',
                'required' => true,
            ])
            ->add('Donation_Product_Label', TextType::class, [
                'label' => 'Product Label',
            ])
            ->add('Donation_Product_Expiration_Date', DateType::class, [
                'label' => 'Product Expiration Date',
                'widget' => 'single_text',
                'required' => true,
            ])            
            ->add('DonationP_Date', DateType::class, [
                'label' => 'Donation Date',
                'widget' => 'single_text',
                'required' => true,
            ])    
            ->add('DonationP_Type', ChoiceType::class, [
                'label' => 'Product Type',
                'choices' => [
                    'Medical' => 'medical',
                    'Hygienic' => 'hygienic',
                    'Food Supplies' => 'food_supplies',
                ],
                'required' => true,
                'placeholder' => 'Select product type', // optionnel, ajoutez ceci si vous voulez une option par défaut
            ])
            ->add('Account_Key',EntityType::class, [
                'class' => Account::class,
                'label' => 'Account',
                'required' => false,    
                'placeholder' => '',
            ])
        ;
        
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationP::class,
        ]);
    }
}
